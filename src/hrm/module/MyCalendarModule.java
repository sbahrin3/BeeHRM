package hrm.module;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import hrm.entity.Employee;
import hrm.entity.EmployeeLeave;
import hrm.entity.Today;
import lebah.db.entity.Persistence;
import lebah.portal.action.Command;

public class MyCalendarModule extends LebahEmployeeModule {
	
	String path = "apps/myCalendar";
	int currentYear, currentMonth, currentDay;
	
	@Override
	public String start() {
		if ( employee == null ) return "apps/not_employee.vm";
		getCalendars();
		context.put("today", new Today());
		return path + "/start.vm";
	}
	
	@Command("getCalendar")
	public String getCalendar() {
		Date today = new Date();
		MyCalendar myc = new MyCalendar(today, employee);
		context.put("c", myc);
		return path + "/calendar.vm";
	}
	
	@Command("getCalendars")
	public String getCalendars() {
		Date today = new Date();
		Calendar todayCalendar = Calendar.getInstance();
		todayCalendar.setTime(today);
		int currentYear = todayCalendar.get(Calendar.YEAR);
		context.put("currentYear", currentYear);
		
		List<MyCalendar> myCalendars = new ArrayList<>();
		context.put("myCalendars", myCalendars);
		for ( int m = 0; m < 12; m++ ) {
			Calendar c = Calendar.getInstance();
			c.set(currentYear, m, 1);
			MyCalendar myc = new MyCalendar(c.getTime(), employee);
			myCalendars.add(myc);

		}
		
		return path + "/calendars.vm";
	}
	

	public static void main(String[] args) {
		
		String empId = "d3d5bb87ca71e40d6c32a0cae7da087ec9e9ec3d";
		Employee employee = Persistence.db().find(Employee.class, empId);
				
		Date today = new Date();
		MyCalendar myc = new MyCalendar(today, employee);
		
		
		for ( int d = 1; d < myc.getTotalDays(); d++ ) {
			System.out.println(d + ")");
			/*
			List<EventCalendar> evs = myc.getEvents(d);
			if ( evs.size() > 0 ) {
				evs.forEach(e -> {
					System.out.println(e.getName() + ", ");
				});
			}
			*/
			
			List<EmployeeLeave> leaves = myc.getApproveLeaves(d);
			if ( leaves.size() > 0 ) {
				leaves.forEach(e -> {
					System.out.println(e.getLeave().getName() + ", ");
				});
			}
		}
			
		
		
		
	}


}
