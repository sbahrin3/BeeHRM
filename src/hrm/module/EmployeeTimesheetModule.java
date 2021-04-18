package hrm.module;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import hrm.entity.Employee;
import hrm.entity.Project;
import hrm.entity.Timesheet;
import lebah.module.LebahUserModule;
import lebah.portal.action.Command;

public class EmployeeTimesheetModule extends LebahUserModule {
	
	String path = "apps/employeeTimesheet";

	@Override
	public String start() {
		listTimesheets();
		return path + "/start.vm";
	}
	
	@Command("listTimesheets")
	public String listTimesheets() {
		
		List<Timesheet> timesheets = db.list("select t from Timesheet t order by t.date desc");
		context.put("timesheets", timesheets);
		
		return path + "/listTimesheets.vm";
	}
	
	@Command("findEmployee")
	public String findEmployee() {
		
		return path + "/findEmployee.vm";
	}
	
	@Command("searchEmployees")
	public String searchEmployees() {
		String searchName = getParam("searchName");
		List<Employee> employees = db.list("select e from Employee e where e.name like '%" + searchName + "%' order by e.name");
		context.put("employees", employees);
		return path + "/searchEmployees.vm";
	}
	
	@Command("addNewTimesheet")
	public String addNewTimesheet() {
		context.remove("timesheet");
		Employee employee = db.find(Employee.class, getParam("employeeId"));
		context.put("employee", employee);
		List<Project> projects = db.list("select p from Project p");
		context.put("projects", projects);
		
		Date today = new Date();
		context.put("today", Util.toStr(today));
		return path + "/timesheet.vm";
	}
	
	@Command("saveNewTimesheet")
	public String saveNewTimesheet() {
		Employee employee = db.find(Employee.class, getParam("employeeId"));
		Timesheet ts = new Timesheet();
		ts.setEmployee(employee);
		ts.setDate(Util.toDate(getParam("timesheetDate")));
		ts.setTimeIn(Util.toTime(getParam("timesheetTimeIn") + " " + getParam("timesheetTimeInAMPM")));
		ts.setTimeOut(Util.toTime(getParam("timesheetTimeOut") + " " + getParam("timesheetTimeOutAMPM")));
		String[] projectIds = request.getParameterValues("projectIds");
		if ( projectIds != null )
			Stream.of(projectIds)
				.map(id -> db.find(Project.class, id))
				.forEach(project -> {
					ts.getProjects().add(project);
				});
		db.save(ts);
		return listTimesheets();
	}
	
	@Command("editTimesheet")
	public String editTimesheet() {
		Timesheet timesheet = db.find(Timesheet.class, getParam("timesheetId"));
		context.put("timesheet", timesheet);
		context.put("employee", timesheet.getEmployee());
		List<Project> projects = db.list("select p from Project p");
		context.put("projects", projects);
		return path + "/timesheet.vm";
	}
	
	@Command("updateTimesheet")
	public String updateTimesheet() {
		Timesheet ts = db.find(Timesheet.class, getParam("timesheetId"));
		
		List<Project> projects = new ArrayList<>();
		projects.addAll(ts.getProjects());
		projects.stream()
			.forEach(project -> {
				ts.getProjects().remove(project);
			});
		db.update(ts);
		
		ts.setDate(Util.toDate(getParam("timesheetDate")));
		ts.setTimeIn(Util.toTime(getParam("timesheetTimeIn") + " " + getParam("timesheetTimeInAMPM")));
		ts.setTimeOut(Util.toTime(getParam("timesheetTimeOut") + " " + getParam("timesheetTimeOutAMPM")));
		
		String[] projectIds = request.getParameterValues("projectIds");
		if ( projectIds != null )
			Stream.of(projectIds)
				.map(id -> db.find(Project.class, id))
				.forEach(project -> {
					ts.getProjects().add(project);
				});
		
		db.update(ts);
		return listTimesheets();
	}
	
	@Command("deleteTimesheet")
	public String deleteTimesheet() {
		context.remove("delete_error");
		Timesheet ts = db.find(Timesheet.class, getParam("timesheetId"));
		try {
			db.delete(ts);
		} catch ( Exception e ) {
			e.printStackTrace();
			context.put("delete_error", e.getMessage());
		}
		return listTimesheets();
	}
}
