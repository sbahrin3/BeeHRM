package hrm.module;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import hrm.entity.Employee;
import hrm.entity.EmployeeLeave;
import hrm.entity.Leave;
import hrm.entity.LeaveEntitlementItem;
import lebah.module.LebahUserModule;
import lebah.portal.action.Command;

/**
 * 
 * @author shamsulbahrin
 *
 */
public class EmployeeLeaveApplicationModule extends LebahUserModule {
	
	
	String path = "apps/employeeLeaveApplication";
	
	
	@Override
	public String start() {
		listEmployeeLeaves();
		return path + "/start.vm";
	}
	
	@Command("listEmployeeLeaves")
	public String listEmployeeLeaves() {
		List<EmployeeLeave> employeeLeaves = db.list("select l from EmployeeLeave l order by l.requestDate desc");
		context.put("employeeLeaves", employeeLeaves);
		
		return path + "/listEmployeeLeaves.vm";
	}
	
	@Command("newLeaveApplication")
	public String newLeaveApplication() {
		
		return path + "/newLeaveApplication.vm";
	}
	
	@Command("searchEmployees")
	public String searchEmployees() {
		String searchName = getParam("searchName");
		List<Employee> employees = db.list("select e from Employee e where e.name like '%" + searchName + "%' order by e.name");
		context.put("employees", employees);
		return path + "/searchEmployees.vm";
	}
	
	@Command("newEmployeeLeave")
	public String newEmployeeLeave() {
		context.remove("employeeLeave");
		
		Employee employee = db.find(Employee.class, getParam("employeeId"));
		context.put("employee", employee);
		
		
		return path + "/employeeLeave.vm";
	}
	
	@Command("saveNewEmployeeLeave")
	public String saveNewEmployeeLeave() {
		
		
		Employee employee = db.find(Employee.class, getParam("employeeId"));
		context.put("employee", employee);
		
		LeaveEntitlementItem item = db.find(LeaveEntitlementItem.class, getParam("leaveEntitlementItemId"));
		Leave leave = item.getLeave();
		
		Date requestToDate = Util.toDate(getParam("requestToDate"));
		Date requestFromDate = Util.toDate(getParam("requestFromDate"));
		
		EmployeeLeave employeeLeave = new EmployeeLeave();
		employeeLeave.setEmployee(employee);
		employeeLeave.setRequestFromDate(requestFromDate);
		employeeLeave.setRequestToDate(requestToDate);
		employeeLeave.setRequestDate(new Date());
		employeeLeave.setLeave(leave);
		employeeLeave.setTotalDays(employeeLeave.getRequestedNumberOfDays());
		db.save(employeeLeave);
		
		return path + "/employeeLeaveSubmitted.vm";
	}
	
	@Command("editEmployeeLeave")
	public String editEmployeeLeave() {
		
		EmployeeLeave employeeLeave = db.find(EmployeeLeave.class, getParam("employeeLeaveId"));
		context.put("employeeLeave", employeeLeave);
		context.put("employee", employeeLeave.getEmployee());
		
		return path + "/employeeLeave.vm";
	}

	@Command("updateEmployeeLeave")
	public String updateEmployeeLeave() {
		
		EmployeeLeave employeeLeave = db.find(EmployeeLeave.class, getParam("employeeLeaveId"));
		
		context.put("employeeLeave", employeeLeave);
		context.put("employee", employeeLeave.getEmployee());
		
		LeaveEntitlementItem item = db.find(LeaveEntitlementItem.class, getParam("leaveEntitlementItemId"));
		Leave leave = item.getLeave();
		
		Date requestToDate = Util.toDate(getParam("requestToDate"));
		Date requestFromDate = Util.toDate(getParam("requestFromDate"));

		employeeLeave.setRequestFromDate(requestFromDate);
		employeeLeave.setRequestToDate(requestToDate);
		employeeLeave.setRequestDate(new Date());
		employeeLeave.setLeave(leave);
		employeeLeave.setTotalDays(employeeLeave.getRequestedNumberOfDays());
		db.update(employeeLeave);
		
		
		return path + "/employeeLeave.vm";
	}
	
	@Command("updateEmployeeLeaveStatus")
	public String updateEmployeeLeaveStatus() {
		
		return path + "/employeeLeave.vm";
	}
	
}
