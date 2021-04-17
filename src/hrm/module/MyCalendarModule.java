package hrm.module;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import hrm.entity.Employee;
import hrm.entity.EmployeeLeave;
import lebah.db.entity.Persistence;
import lebah.db.entity.User;
import lebah.module.LebahUserModule;
import lebah.portal.action.Command;

public class MyCalendarModule extends LebahUserModule {
	
	String path = "apps/myCalendar";
	int currentYear, currentMonth, currentDay;
	Employee employee;
	
	@Override
	public String start() {
		
		User user = (User) context.get("user");
		employee = user.getEmployee();
		
		getCalendar();
		return path + "/start.vm";
	}
	
	@Command("getCalendar")
	public String getCalendar() {
		Date today = new Date();
		MyCalendar myc = new MyCalendar(today, employee);
		context.put("c", myc);
		
		Calendar c1 = Calendar.getInstance();
		c1.set(myc.getYear(), myc.getMonth(), 1);
		c1.add(Calendar.DATE, -1);
		
		return path + "/calendar.vm";
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
