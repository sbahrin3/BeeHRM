package hrm.module;

import java.util.List;

import hrm.entity.Department;
import hrm.entity.Employee;
import hrm.entity.EmployeeJob;
import hrm.entity.Job;
import lebah.module.LebahUserModule;
import lebah.portal.action.Command;

public class ManageEmployeesModule extends LebahUserModule {
	
	String path = "apps/manageEmployees";
	
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
	
	@Command("saveNewEmployee")
	public String saveNewEmployee() {
		
		Employee employee = new Employee();
		employee.setName(getParam("employeeName"));
		employee.setIdNumber(getParam("employeeIdNumber"));
		
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
		employee.setName(getParam("employeeName"));
		employee.setIdNumber(getParam("employeeIdNumber"));
		
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
		Employee employee = db.find(Employee.class, getParam("employeeId"));
		context.put("employee", employee);
		
		SelectList.listCompanies(context);
		SelectList.listJobs(context);
		
		return path + "/employeeJob.vm";
		
	}
	

}
