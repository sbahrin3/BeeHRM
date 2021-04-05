package hrm.module;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import hrm.entity.Employee;
import hrm.entity.EmployeeLeave;
import hrm.entity.Leave;
import hrm.entity.LeaveEntitlementItem;
import lebah.db.entity.Persistence;
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
		employeeLeave.setRemark(getParam("employeeLeaveRemark"));
		db.save(employeeLeave);
		
		return listEmployeeLeaves();
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
		employeeLeave.setRemark(getParam("employeeLeaveRemark"));
		db.update(employeeLeave);
		
		
		return path + "/employeeLeave.vm";
	}
	
	@Command("editEmployeeLeaveStatus")
	public String editEmployeeLeaveStatus() {
		
		EmployeeLeave employeeLeave = db.find(EmployeeLeave.class, getParam("employeeLeaveId"));
		context.put("employeeLeave", employeeLeave);
		context.put("employee", employeeLeave.getEmployee());
		
		int daysAvailable = checkAvailableDays();
		
		
		return path + "/employeeLeaveStatus.vm";
	}
	

	@Command("updateEmployeeLeaveStatus")
	public String updateEmployeeLeaveStatus() {
		
		EmployeeLeave employeeLeave = db.find(EmployeeLeave.class, getParam("employeeLeaveId"));
		context.put("employeeLeave", employeeLeave);
		context.put("employee", employeeLeave.getEmployee());
		
		employeeLeave.setStatus(Util.getInt(getParam("leaveStatus")));
		employeeLeave.setRemark(getParam("employeeLeaveRemark"));
		
		employeeLeave.setApproveDate(employeeLeave.getStatus() == 2 ? new Date() : null);
		employeeLeave.setApproveFromDate(employeeLeave.getStatus() == 2 ? employeeLeave.getRequestFromDate() : null);
		employeeLeave.setApproveToDate(employeeLeave.getStatus() == 2 ? employeeLeave.getRequestToDate() : null);
	
		db.update(employeeLeave);
		
		return listEmployeeLeaves();
	}
	
	@Command("deleteEmployeeLeave")
	public String deleteEmployeeLeave() {
		context.remove("delete_error");
		EmployeeLeave employeeLeave = db.find(EmployeeLeave.class, getParam("employeeLeaveId"));
		try {
			db.delete(employeeLeave);
		} catch ( Exception e ) {
			context.put("delete_error", e.getMessage());
			e.printStackTrace();
		}
		return listEmployeeLeaves();
	}
	
	@Command("checkEmployeeLeaveStatus")
	public String checkEmployeeLeaveStatus() {
		
		checkAvailableDays();
		return path + "/checkStatus.vm";
	}
	
	private int checkAvailableDays() {
		Employee employee = db.find(Employee.class, getParam("employeeId"));
		
		LeaveEntitlementItem item = db.find(LeaveEntitlementItem.class, getParam("leaveEntitlementItemId"));
		int daysEntitled = 0;
		if ( item != null ) {
			daysEntitled = item.getNumberOfDays();
		} else {
			EmployeeLeave employeeLeave = db.find(EmployeeLeave.class, getParam("employeeLeaveId"));
			System.out.println("=== " + employeeLeave);
			Leave leave = employeeLeave.getLeave();
			employee = employeeLeave.getEmployee();
			
			Optional<LeaveEntitlementItem> opt = employee.getLeaveEntitlement().getItems().stream().filter(i -> i.getLeave().getId().equals(leave.getId())).findFirst();
			if ( opt.isPresent() ) {
				item = opt.get();
				daysEntitled = item.getNumberOfDays();
			}
		}
		
		List<EmployeeLeave> employeeLeaves = db.list("select l from EmployeeLeave l where l.employee.id = '" + employee.getId() + "' and l.leave.id = '" + item.getLeave().getId() + "'");
		int daysTaken = employeeLeaves.stream().collect(Collectors.summingInt(l -> l.getApprovedNumberOfDays()));
		int daysAvailable = daysEntitled - daysTaken;
		
		context.put("employee", employee);
		context.put("leave", item.getLeave());
		context.put("daysEntitled", daysEntitled);
		context.put("daysTaken", daysTaken);
		context.put("daysAvailable", daysAvailable);
		
		return daysAvailable;
	}
	
	@Command("checkDays")
	public String checkDays() {
		
		boolean accepted = false;
				
		Date requestToDate = Util.toDate(getParam("requestToDate"));
		Date requestFromDate = Util.toDate(getParam("requestFromDate"));
		
		long requestedNumberOfDays = 0;
		if ( requestToDate != null && requestFromDate != null ) {
			
			int daysAvailable = checkAvailableDays();
			long diffInMillies = Math.abs(requestToDate.getTime() - requestFromDate.getTime());
			requestedNumberOfDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
			accepted = requestedNumberOfDays <= daysAvailable;
			
		}
		context.put("requestedNumberOfDays", requestedNumberOfDays);
		context.put("accepted", accepted);
		
		return path + "/checkDays.vm";
	}
	
	
	public static void main(String[] args) {
		
		String id = "d3d5bb87ca71e40d6c32a0cae7da087ec9e9ec3d";
		Employee employee = Persistence.db().find(Employee.class, id);
		List<EmployeeLeave> employeeLeaves = Persistence.db().list("select l from EmployeeLeave l where l.employee.id = '" + employee.getId() + "'");

		int totalDays = employeeLeaves.stream().collect(Collectors.summingInt(l -> l.getApprovedNumberOfDays()));
		System.out.println("Total Days = " + totalDays);
		
	}
	
}
