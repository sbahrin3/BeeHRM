package hrm.module;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import hrm.entity.EventCalendar;
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

}
