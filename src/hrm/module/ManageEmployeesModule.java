package hrm.module;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hrm.entity.Department;
import hrm.entity.Employee;
import hrm.entity.EmployeeJob;
import hrm.entity.EmployeeLeave;
import hrm.entity.Job;
import hrm.entity.Leave;
import hrm.entity.LeaveCarryForward;
import hrm.entity.LeaveEntitlement;
import hrm.entity.LeaveEntitlementItem;
import hrm.entity.SalaryAllowance;
import hrm.entity.SalaryConfig;
import hrm.entity.SalaryDeductionItem;
import hrm.entity.SalaryItem;
import lebah.db.entity.Persistence;
import lebah.module.LebahUserModule;
import lebah.portal.action.Command;

public class ManageEmployeesModule extends LebahUserModule {
	
	String path = "apps/manageEmployees";
	
	public void preProcess() {
		super.preProcess();
		context.put("currentYear", 2021);
	}
	
	@Override
	public String start() {
		listEmployees();
		return path + "/start.vm";
	}

	@Command("listEmployees")
	public String listEmployees() {
		
		List<Employee> employees = db.list("select e from Employee e order by e.name");
		context.put("employees", employees);
		
		return path + "/listEmployees.vm";
	}
	
	@Command("addNewEmployee")
	public String addNewEmployee() {
		
		context.remove("employee");
		SelectList.listCompanies(context);
		SelectList.listJobs(context);
		return path + "/employee.vm";
	}
	
	@Command("selectDepartments")
	public String selectDepartments() {
		SelectList.listDepartments(context, getParam("companyId"));
		return path + "/selectDepartments.vm";
	}
	
	private void setEmployeeData(Employee employee) {
		employee.setName(getParam("employeeName"));
		employee.setIdNumber(getParam("employeeIdNumber"));
		employee.setIdentityType(getParam("employeeIdentityType"));
		employee.setIdentityNumber(getParam("employeeIdentityNumber"));
		employee.setGender(Util.getInt(getParam("employeeGender")));
		employee.setBirthDate(Util.toDate(getParam("employeeBirthDate")));
		employee.setBirthPlace(getParam("employeeBirthPlace"));
		employee.getAddress().setTelephone(getParam("employeeTelephone"));
		employee.getAddress().setEmail(getParam("employeeEmail"));
	}
	
	@Command("saveNewEmployee")
	public String saveNewEmployee() {
		
		Employee employee = new Employee();
		setEmployeeData(employee);
		
		db.save(employee);
		
		context.put("employee", employee);
		
		return path + "/employee.vm";
	}
	
	@Command("editEmployee")
	public String editEmployee() {
		
		Employee employee = db.find(Employee.class, getParam("employeeId"));
		context.put("employee", employee);
		
		return path + "/employee.vm";
	}
	
	@Command("updateEmployee")
	public String updateEmployee() {
		
		Employee employee = db.find(Employee.class, getParam("employeeId"));
		setEmployeeData(employee);
		
		db.update(employee);
		
		context.put("employee", employee);
		
		return path + "/employee.vm";
	}
	
	@Command("deleteEmployee")
	public String deleteEmployee() {
		
		context.remove("delete_error");
		Employee employee = db.find(Employee.class, getParam("employeeId"));
		try {
			db.delete(employee);
		} catch ( Exception e ) {
			context.put("delete_error", "Constraint violation... can't delete!");
		}
		
		return listEmployees();
	}
	
	@Command("listEmployeeJobs")
	public String listEmployeeJobs() {
		
		Employee employee = db.find(Employee.class, getParam("employeeId"));
		context.put("employee", employee);
		
		return path + "/listEmployeeJobs.vm";
	}
	
	@Command("addNewEmployeeJob")
	public String addNewEmployeeJob() {
		
		context.remove("employeeJob");
		
		Employee employee = db.find(Employee.class, getParam("employeeId"));
		context.put("employee", employee);
		
		SelectList.listCompanies(context);
		SelectList.listJobs(context);
		
		return path + "/employeeJob.vm";
		
	}
	
	private void setPrimaryJob(EmployeeJob employeeJob) throws Exception {
		if ( "1".equals(getParam("isPrimaryJob"))) { //only on job can become primary
			List<EmployeeJob> list = new ArrayList<>();
			employeeJob.getEmployee().getJobs().stream()
				.filter(ej -> !ej.getId().equals(employeeJob.getId()))
				.forEach(ej -> {
					ej.setPrimaryJob(false);
					list.add(ej);
				}
			);
			db.update(list.toArray());
			employeeJob.setPrimaryJob(true);
		}
	}
	
	@Command("saveNewEmployeeJob")
	public String saveNewEmployeeJob() {
		
		Employee employee = db.find(Employee.class, getParam("employeeId"));
		context.put("employee", employee);
		
		Job job = db.find(Job.class, getParam("jobId"));
		Department department = db.find(Department.class, getParam("departmentId"));
		
		EmployeeJob employeeJob = new EmployeeJob();
		employeeJob.setEmployee(employee);
		employeeJob.setDepartment(department);
		employeeJob.setJob(job);
		
		employeeJob.setStartDate(Util.toDate(getParam("employeeJobStartDate")));
		employeeJob.setEndDate(Util.toDate(getParam("employeeJobEndDate")));
		
		try {
			setPrimaryJob(employeeJob);
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		
		db.save(employeeJob);
		
		employee.getJobs().add(employeeJob);
		db.update(employee);
				
		return listEmployeeJobs();
	}
	
	@Command("editEmployeeJob")
	public String editEmployeeJob() {
		
		EmployeeJob employeeJob = db.find(EmployeeJob.class, getParam("employeeJobId"));
		context.put("employeeJob", employeeJob);
		
		context.put("employee", employeeJob.getEmployee());
		
		SelectList.listCompanies(context);
		SelectList.listJobs(context);
		SelectList.listDepartments(context, employeeJob.getDepartment().getCompany().getId());
		
		return path + "/employeeJob.vm";
	}
	
	@Command("updateEmployeeJob")
	public String updateEmployeeJob() {
		
		Job job = db.find(Job.class, getParam("jobId"));
		Department department = db.find(Department.class, getParam("departmentId"));
		
		EmployeeJob employeeJob = db.find(EmployeeJob.class, getParam("employeeJobId"));
		employeeJob.setDepartment(department);
		employeeJob.setJob(job);
		
		employeeJob.setStartDate(Util.toDate(getParam("employeeJobStartDate")));
		employeeJob.setEndDate(Util.toDate(getParam("employeeJobEndDate")));
		
		try {
			setPrimaryJob(employeeJob);
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		
		db.update(employeeJob);
		
		try {
			calculateEmployeeJobSalary(db, employeeJob);
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		
		return listEmployeeJobs();
	}


	@Command("deleteEmployeeJob")
	public String deleteEmployeeJob() {
		context.remove("delete_error");
		EmployeeJob employeeJob = db.find(EmployeeJob.class, getParam("employeeJobId"));
		Employee employee = employeeJob.getEmployee();
		try {
			db.delete(employeeJob);
			
			employee.getJobs().remove(employeeJob);
			db.update(employee);
			
		} catch ( Exception e ) {
			context.put("delete_error", "Constraint violation... can't delete!");
		}
		
		return listEmployeeJobs();
	}
	
	@Command("updateSalary")
	public String updateSalary() {
		
		EmployeeJob employeeJob = db.find(EmployeeJob.class, getParam("employeeJobId"));
		Employee employee = employeeJob.getEmployee();
		context.put("employeeJob", employeeJob);
		context.put("employee", employee);
		
		employeeJob.getSalary().setBasicAmount(Util.getDouble(getParam("employeeJobBasicAmount")));
		
		try {
			db.update(employeeJob);
			calculateEmployeeJobSalary(db, employeeJob);
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		
		return path + "/employeeJobSalary.vm";
	}
	
	
	@Command("getEmployeeJobSalary")
	public String getEmployeJobSalary() {
		
		EmployeeJob employeeJob = db.find(EmployeeJob.class, getParam("employeeJobId"));
		Employee employee = employeeJob.getEmployee();
		context.put("employeeJob", employeeJob);
		context.put("employee", employee);
		
		return path + "/employeeJobSalary.vm";
	}
	
	
	@Command("listSalaryConfigs")
	public String listSalaryConfigs() {
		
		EmployeeJob employeeJob = db.find(EmployeeJob.class, getParam("employeeJobId"));
		Employee employee = employeeJob.getEmployee();
		context.put("employeeJob", employeeJob);
		context.put("employee", employee);
		
		List<SalaryConfig> salaryConfigs = db.list("select s from SalaryConfig s");
		context.put("salaryConfigs", salaryConfigs);
		
		return path + "/listSalaryConfigs.vm";
	}
	
	@Command("selectSalaryConfig")
	public String selectSalaryConfig() {
		
		EmployeeJob employeeJob = db.find(EmployeeJob.class, getParam("employeeJobId"));
		Employee employee = employeeJob.getEmployee();
		context.put("employeeJob", employeeJob);
		context.put("employee", employee);
		
		SalaryConfig salaryConfig = db.find(SalaryConfig.class, getParam("salaryConfigId"));
		
		employeeJob.getSalary().setSalaryConfig(salaryConfig);
		
		db.update(employeeJob);
		
		try {
			calculateEmployeeJobSalary(db, employeeJob);
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		
		return path + "/employeeJobSalary.vm";
	}
	
	@Command("viewSalary")
	public String viewSalary() {
		
		EmployeeJob employeeJob = db.find(EmployeeJob.class, getParam("employeeJobId"));
		Employee employee = employeeJob.getEmployee();
		context.put("employeeJob", employeeJob);
		context.put("employee", employee);
		
		return path + "/viewSalary.vm";
	}

	private static void calculateEmployeeJobSalary(Persistence db, EmployeeJob employeeJob) throws Exception {
		db.delete(employeeJob.getSalaryItems().toArray());
		employeeJob.getSalaryItems().clear();
		db.update(employeeJob);
				
		double basicAmount = employeeJob.getSalary().getBasicAmount();
		double grossAmount = basicAmount;
		double netAmount = 0.0d;
		List<SalaryDeductionItem> deductions = employeeJob.getSalary().getSalaryConfig().getDeductions();
		List<SalaryAllowance> allowances = employeeJob.getSalary().getSalaryConfig().getAllowances();
		
		List<SalaryItem> salaryItems = new ArrayList<>();
		int i = 0;
		SalaryItem salaryItem = new SalaryItem(i, "BAS", "Basic Salary", employeeJob.getSalary().getBasicAmount());
		salaryItem.setEmployeeJob(employeeJob);
		salaryItems.add(salaryItem);
		for ( SalaryAllowance allowance : allowances ) {
			i++;
			salaryItem = new SalaryItem(i, allowance.getName(), allowance.getDescription(), allowance.getAmount());
			salaryItem.setEmployeeJob(employeeJob);
			salaryItems.add(salaryItem);
			grossAmount += allowance.getAmount();
		}
		for ( SalaryDeductionItem deduction : deductions ) {
			i++;
			double amountDeduct = 0.0d;
			if ( deduction.isUseRate()) {
				if ( deduction.isRateOnBasicOnly() ) {
					amountDeduct -= ( (double) deduction.getRate() / 100 ) * basicAmount;
				}
				else {
					amountDeduct -= ( (double) deduction.getRate() / 100 ) * grossAmount;
				}
			} else {
				amountDeduct -= deduction.getAmount();
			}	
			salaryItem = new SalaryItem(i, deduction.getName(), deduction.getDescription(), amountDeduct);
			salaryItem.setEmployeeJob(employeeJob);
			salaryItems.add(salaryItem);
		}
		
		db.save(salaryItems.toArray());
		
		netAmount = salaryItems.stream().collect(Collectors.summingDouble(item -> item.getAmount()));
		employeeJob.setSalaryItems(salaryItems);
		employeeJob.getSalary().setGrossAmount(grossAmount);
		employeeJob.getSalary().setNetAmount(netAmount);
		
		db.update(employeeJob);
	}
	
	@Command("selectLeaveEntitlement")
	public String selectLeaveEntitlement() {
		
		Employee employee = db.find(Employee.class, getParam("employeeId"));
		context.put("employee", employee);
		
		List<LeaveEntitlement> leaveEntitlements = db.list("select l from LeaveEntitlement l");
		context.put("leaveEntitlements", leaveEntitlements);
		
		return path + "/selectLeaveEntitlement.vm";
	}
	
	@Command("saveLeaveEntitlement")
	public String saveLeaveEntitlement() {
		
		Employee employee = db.find(Employee.class, getParam("employeeId"));
		context.put("employee", employee);
		
		LeaveEntitlement leaveEntitlement = db.find(LeaveEntitlement.class, getParam("leaveEntitlementId"));
		employee.setLeaveEntitlement(leaveEntitlement);
		
		db.update(employee);
		
		return path + "/leaveEntitlement.vm";
	}
	
	@Command("cancelSelectLeaveEntitlement")
	public String cancelSelectLeaveEntitlement() {
		Employee employee = db.find(Employee.class, getParam("employeeId"));
		context.put("employee", employee);
		return path + "/leaveEntitlement.vm";		
	}
	
	@Command("editLeaveCarryForward")
	public String editLeaveCarryForward() {
		Employee employee = db.find(Employee.class, getParam("employeeId"));
		context.put("employee", employee);
		LeaveEntitlementItem item = db.find(LeaveEntitlementItem.class, getParam("leaveEntitlementItemId"));
		context.put("leaveEntitlementItem", item);
		context.put("leave", item.getLeave());
		
		return path + "/leaveCarryForward.vm";
	}
	
	@Command("saveLeaveCarryForward")
	public String saveLeaveCarryForward() {
		Employee employee = db.find(Employee.class, getParam("employeeId"));
		context.put("employee", employee);
		LeaveEntitlementItem item = db.find(LeaveEntitlementItem.class, getParam("leaveEntitlementItemId"));
		Leave leave = item.getLeave();
		LeaveCarryForward leaveCarry = db.get("select c from LeaveCarryForward c where c.employee.id = '" + employee.getId()+  "' and c.leave.id = '" + leave.getId() + "' ");
		if ( leaveCarry == null ) {
			leaveCarry = new LeaveCarryForward();
			leaveCarry.setEmployee(employee);
			leaveCarry.setLeave(leave);
			leaveCarry.setYear(Util.getInt(getParam("currentYear")));
			leaveCarry.setNumberOfDays(Util.getInt(getParam("numberOfDays")));
			db.save(leaveCarry);
		}
		else {
			leaveCarry.setYear(Util.getInt(getParam("currentYear")));
			leaveCarry.setNumberOfDays(Util.getInt(getParam("numberOfDays")));
			db.update(leaveCarry);
		}
		
		if ( !employee.getLeaveCarryForwards().contains(leaveCarry)) {
			employee.getLeaveCarryForwards().add(leaveCarry);
			db.update(employee);
		}
		
		return path + "/leaveEntitlement.vm";
	}
	
	public static void main(String[] args) {
		
		Persistence db = Persistence.db();
		String id = "d3d5bb87ca71e40d6c32a0cae7da087ec9e9ec3d";
		
		Employee employee = db.find(Employee.class, id);
		List<EmployeeLeave> employeeLeaves = employee.getEmployeeLeaves();
		
		String leaveId = "5f4b530b3c53d757e98fbb148b3e10de6326be12";
		Leave leave = db.find(Leave.class, leaveId);
		
		int sum = employeeLeaves.stream()
				.filter(l -> l.getLeave().getId().equals(leave.getId()))
				.collect(Collectors.summingInt(l -> l.getApprovedNumberOfDays()));
		
	}
	


}
