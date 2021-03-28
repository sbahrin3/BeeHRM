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
		
		Department department = db.find(Department.class, getParam("departmentId"));
		Job job = db.find(Job.class, getParam("jobId"));
		
		EmployeeJob employeeJob = new EmployeeJob();
		
		Employee employee = new Employee();
		employee.setName(getParam("employeeName"));
		employee.setIdNumber(getParam("employeeIdNumber"));
		
		
		return path + "/employee.vm";
	}
	
	@Command("editEmployee")
	public String editEmployee() {
		
		return path + "/employee.vm";
	}

	@Command("updateEmployee")
	public String updateEmployee() {
		
		return path + "/employee.vm";
	}
	
	@Command("deleteEmployee")
	public String deleteEmployee() {
		
		return listEmployees();
	}
}
