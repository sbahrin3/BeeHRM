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

import hrm.entity.Employee;
import hrm.entity.EventCalendar;
import hrm.entity.Leave;
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
		return (int) numberOfDays;
	}
	
	public static int getNumberOfHolidays(Date fromDate, Date toDate) {
		Params<String, Object> params = new Params<>();
		params.put("fromDate", fromDate);
		params.put("toDate", toDate);
		List<EventCalendar> holidays = Persistence.db().list("select e from EventCalendar e where e.holiday = 1 and e.fromDate >= :fromDate and e.toDate <= :toDate", params);
		return holidays.stream()
                        .map(e -> numberOfDaysBetween(e.getFromDate(), e.getToDate()))
                        .collect(Collectors.summingInt(Integer::intValue));
		
	}

	
	public static int getNumberOfWeekends(Date fromDate, Date toDate) {
		/*
		 * Somehow, getting date from database cannot be recognize when try to convert to localdate
		 * so need to do these workaround first,
		 * convert to String representation and convert to Date back
		 */
		String fromDateStr = new SimpleDateFormat("dd/MM/yyyy").format(fromDate);
		String toDateStr = new SimpleDateFormat("dd/MM/yyyy").format(toDate);
		
		Predicate<LocalDate> isWeekend = date -> date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
		long daysBetween = ChronoUnit.DAYS.between(toLocalDate(fromDateStr), toLocalDate(toDateStr));
		long weekends = Stream.iterate(toLocalDate(fromDateStr), date -> date.plusDays(1)).limit(daysBetween)
                                  .filter(isWeekend).count();
		return (int) weekends;
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
		
		String empid = "d3d5bb87ca71e40d6c32a0cae7da087ec9e9ec3d";
		String leaveid = "5f4b530b3c53d757e98fbb148b3e10de6326be12";
		
		Employee emp = db.find(Employee.class, empid);
		Leave leave = db.find(Leave.class, leaveid);
		
		int days = emp.getLeaveDaysTaken(leave, 2021);
		
		System.out.println(days);
		
		
		//get localdate from 2021-05-09

		  

	}
	

}
