package hrm.entity;

import java.util.Calendar;
import java.util.Date;

import hrm.module.Util;

public class Today {
	
	Date date = new Date();
	Calendar todayCalendar;
	
	public Today() {
		todayCalendar = Calendar.getInstance();
		todayCalendar.setTime(date);
	}
	
	public String getDateStr() {
		return Util.toDateStr(date);
	}
	
	public String getTimeStr() {
		return Util.toTimeStr(date);
	}
	
	public String getTimeStr1() {
		return getTimeStr().length() > 4 ? getTimeStr().substring(0, 5) : "";
	}
	
	public String getTimeStr2() {
		return  getTimeStr().length() > 5 ? getTimeStr().substring(6) : "";
	}
	
	public int getYear() {
		return todayCalendar.get(Calendar.YEAR);
	}
	
	public int getMonth() {
		return todayCalendar.get(Calendar.MONTH);
	}

}
