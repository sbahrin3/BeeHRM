package hrm.module;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import hrm.entity.Employee;
import hrm.entity.EmployeeLeave;
import hrm.entity.EventCalendar;
import lebah.db.entity.Persistence;

public class MyCalendar {
	
	static String[] dayNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
	static String[] monthNames = {"January", "February", "March", "April", "May", "Jun", "July", "August", "September", "October", "November", "December" };
	
	Calendar calendar;
	Employee employee;
	
	List<EventCalendar> events = new ArrayList<>();
	List<EmployeeLeave> leaves = new ArrayList<>();

	
	public MyCalendar(Date date, Employee employee) {
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		this.employee = employee;
		retrieveEvents();
		retrieveLeaves();
	}
	
	public String getTitle() {
		return monthNames[getMonth()] + ", " + getYear(); 
	}
	
	public int getYear() {
		return calendar.get(Calendar.YEAR);
	}

	public int getMonth() {
		return calendar.get(Calendar.MONTH);
	}

	public int getDay() {
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	public int getTotalDays() {
		return calendar.getMaximum(Calendar.DAY_OF_MONTH);
	}

	public String getDayName() {
		return dayNames[calendar.get(Calendar.DAY_OF_WEEK) - 1];
	}
	
	public String getDayName(int day) {
		Calendar c = Calendar.getInstance();
		c.set(getYear(), getMonth(), day);
		return dayNames[c.get(Calendar.DAY_OF_WEEK) - 1];
	}
	
	
	
	
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public List<EventCalendar> getEvents() {
		return events;
	}

	public void setEvents(List<EventCalendar> events) {
		this.events = events;
	}
	
	
	
	public List<EventCalendar> getEvents(int day) {
		Calendar c = Calendar.getInstance();
		c.set(getYear(), getMonth(), day);
		
		
		return events.stream()
                     .filter(e -> e.getFromDay() == day || e.getToDay() == day || (c.after(e.getFromDateCalendar()) && c.before(e.getToDateCalendar()) ))
                     .collect(Collectors.toList());
	}
	
	public List<EmployeeLeave> getLeaves(int day) {
		Calendar c = Calendar.getInstance();
		c.set(getYear(), getMonth(), day);
		
		return leaves.stream()
                     .filter(e -> e.getApproveFromDay() == day || e.getApproveToDay() == day || (c.after(e.getApproveFromDateCalendar()) && c.before(e.getApproveToDateCalendar()) ))
                     .collect(Collectors.toList());
	}
	
	private void retrieveEvents() {
		Calendar c1 = Calendar.getInstance();
		c1.set(getYear(), getMonth(), 1);
		c1.add(Calendar.DATE, -1);
				
		Calendar c2 = Calendar.getInstance();
		c2.set(getYear(), getMonth(), getTotalDays()-1);
		c2.add(Calendar.DATE, 1);
				
		Params<String, Object> p = new Params<>();
		p.put("date1", c1.getTime());
		p.put("date2", c2.getTime());
		
		events = Persistence.db().list("select e from EventCalendar e where e.fromDate >= :date1 and e.fromDate <= :date2", p);
		
	}
	
	private void retrieveLeaves() {
		Calendar c1 = Calendar.getInstance();
		c1.set(getYear(), getMonth(), 1);
		c1.add(Calendar.DATE, -1);
				
		Calendar c2 = Calendar.getInstance();
		c2.set(getYear(), getMonth(), getTotalDays()-1);
		c2.add(Calendar.DATE, 1);
				
		Params<String, Object> p = new Params<>();
		p.put("employeeId", employee.getId());
		p.put("date1", c1.getTime());
		p.put("date2", c2.getTime());
		
		leaves = Persistence.db().list("select e from EmployeeLeave e where e.employee.id = :employeeId and e.approveFromDate >= :date1 and e.approveFromDate <= :date2", p);
		
	}

	public static void main(String[] args) {
	
	}

}
