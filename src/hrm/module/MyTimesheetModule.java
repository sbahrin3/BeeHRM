package hrm.module;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import hrm.entity.Employee;
import hrm.entity.Project;
import hrm.entity.Timesheet;
import lebah.db.entity.User;
import lebah.module.LebahUserModule;
import lebah.portal.action.Command;

public class MyTimesheetModule extends LebahUserModule {
	
	String path = "apps/myTimesheet";
	Employee employee;
	
	@Override
	public void preProcess() {
		super.preProcess();
		User user = (User) context.get("user");
		employee = user.getEmployee();
		context.put("employee", employee);
	}
	
	@Override
	public String start() {
		listTimesheets();
		return path + "/start.vm";
	}
	
	@Command("listTimesheets")
	public String listTimesheets() {
		
		List<Timesheet> timesheets = db.list("select t from Timesheet t where t.employee.id = '" + employee.getId() + "' order by t.date desc");
		context.put("timesheets", timesheets);
		
		return path + "/listTimesheets.vm";
	}
	
	@Command("addNewTimesheet")
	public String addNewTimesheet() {
		context.remove("timesheet");
		List<Project> projects = db.list("select p from Project p");
		context.put("projects", projects);
		return path + "/timesheet.vm";
	}
	
	@Command("saveNewTimesheet")
	public String saveNewTimesheet() {
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
