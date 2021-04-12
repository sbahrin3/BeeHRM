package hrm.module;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import hrm.entity.Company;
import hrm.entity.Employee;
import hrm.entity.EmployeeJob;
import hrm.entity.EventCalendar;
import hrm.entity.Office;
import hrm.entity.State;
import lebah.db.entity.Persistence;

public class Util {
	
	public static double getDouble(String value) {
		double d = 0.0d;
		if ( value.equals("") ) return d;
		d = Double.parseDouble(value);
		return d;
	}
	
	public static int getInt(String value) {
		int d = 0;
		if ( value.equals("") ) return d;
		d = Integer.parseInt(value);
		return d;
	}
	
	public static Date toDate(String dateStr) {
		Date date = null;
		try {
			date = new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);
		} catch ( Exception e ) {
			//e.printStackTrace();
		}
		return date;
	}
	
	public static String toStr(Date date) {
		return date != null ? new SimpleDateFormat("dd/MM/yyyy").format(date) : "";
	}
	
	
	public static int numberOfDaysBetween(Date fromDate, Date toDate) {
		long diffInMillies = Math.abs(toDate.getTime() - fromDate.getTime());
		long numberOfDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);;
		return (int) numberOfDays + 1;
	}
	
	public static int getNumberOfHolidays(Date fromDate, Date toDate, State state) {
		Params<String, Object> params = new Params<>();
		params.put("fromDate", fromDate);
		params.put("toDate", toDate);
		params.put("state", state);
		List<EventCalendar> holidays = Persistence.db().list("select e from EventCalendar e Join e.states s where e.holiday = 1 and e.fromDate >= :fromDate and e.toDate <= :toDate and s = :state", params);
		return holidays.stream()
                        .map(e -> numberOfDaysBetween(e.getFromDate(), e.getToDate()))
                        .collect(Collectors.summingInt(Integer::intValue));
		
	}
	
	public static int getNumberOfHolidays(Date fromDate, Date toDate, Employee employee) {
		State state = null;
		try {
			Office office = employee.getOffice();
			if ( office != null ) 
				state = office.getAddress().getDistrict().getState();
			if ( state == null ) {
				EmployeeJob employeeJob = employee.getPrimaryEmployeeJob();
				office = employeeJob.getDepartment().getCompany().getOffices().get(0);
				state = office.getAddress().getDistrict().getState(); 
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			Company company = Persistence.db().find(Company.class, "HQ");
			Office office = company.getOffices().get(0);
			if ( office != null ) state = office.getAddress().getDistrict().getState();
		}
		return getNumberOfHolidays(fromDate, toDate, state);
	}

	public static int getNumberOfWeekends(Date fromDate, Date toDate) {
		return getNumberOfWeekends(fromDate, toDate, 1);
	}
	
	public static int getNumberOfWeekends(Date fromDate, Date toDate, int weekendType) {
		/*
		 * Somehow, getting date from database cannot be recognize when try to convert to localdate
		 * so need to do these workaround first,
		 * convert to String representation and convert to Date back
		 */
		String fromDateStr = new SimpleDateFormat("dd/MM/yyyy").format(fromDate);
		String toDateStr = new SimpleDateFormat("dd/MM/yyyy").format(toDate);
		
		Predicate<LocalDate> weekendTypePredicate = filterByWeekendType(weekendType);
		
		long daysBetween = ChronoUnit.DAYS.between(toLocalDate(fromDateStr), toLocalDate(toDateStr)) + 1;
		long weekends = Stream.iterate(toLocalDate(fromDateStr), date -> date.plusDays(1)).limit(daysBetween)
                              .filter(weekendTypePredicate).count();
		
		return (int) weekends;
	}
	
	static Predicate<LocalDate> wkSaturdayAndSunday = date -> date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
	static Predicate<LocalDate> wkFridayAndSaturday = date -> date.getDayOfWeek() == DayOfWeek.FRIDAY || date.getDayOfWeek() == DayOfWeek.SATURDAY;
	static Predicate<LocalDate> wkSundayOnly = date -> date.getDayOfWeek() == DayOfWeek.SUNDAY;
	static Predicate<LocalDate> wkSaturdayOnly = date -> date.getDayOfWeek() == DayOfWeek.SATURDAY;
	static Predicate<LocalDate> wkFridayOnly = date -> date.getDayOfWeek() == DayOfWeek.FRIDAY;
	
	static Predicate<LocalDate> filterByWeekendType(int type) {
		
		switch (type) {
		case 1:
			return wkSaturdayAndSunday;
		case 2:
			return wkFridayAndSaturday;
		case 3:
			return wkSundayOnly;
		case 4:
			return wkSaturdayOnly;
		case 5:
			return wkFridayOnly;
		}
		
		return wkSaturdayAndSunday;
	}
	
	public static LocalDate toLocalDate(String dateStr) {
		Date date = Util.toDate(dateStr);
		return toLocalDate(date);
	}
	
	public static LocalDate toLocalDate(Date date) {
	    return date.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDate();
	}
	
	public static void main(String[] args) {
		
		Persistence db = Persistence.db();

		
		//State state = db.find(State.class, "05");
		//System.out.println(state.getName());
		
		int days = getNumberOfWeekends(Util.toDate("17/04/2021"), Util.toDate("18/04/2021"));
		System.out.println(days);

	}
	

}
