package hrm.module;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
	
	

}
