
package hrm.module;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import hrm.entity.Client;
import hrm.entity.Employee;
import hrm.entity.Location;
import hrm.entity.Project;
import hrm.entity.Timesheet;
import hrm.entity.TimesheetLocation;
import lebah.db.entity.Persistence;
import lebah.db.entity.User;
import lebah.module.LebahUserModule;
import lebah.portal.action.Command;

public class MyTimesheetModule extends LebahEmployeeModule {
	
	String path = "apps/myTimesheet";
	
	@Override
	public String start() {
		if ( employee == null ) return "apps/not_employee.vm";
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
		List<Project> projects = db.list("select p from Project p order by p.name");
		context.put("projects", projects);
		
		context.remove("myprojects");
		List<Project> myprojects = new ArrayList<>();
		List<Timesheet> timesheets = db.list("select t from Timesheet t order by t.date desc");
		if ( timesheets.size() > 0 ) {
			Timesheet t = timesheets.get(0);
			if ( t.getProjects().size() > 0 ) t.getProjects().forEach(p -> myprojects.add(p));
			context.put("myprojects", myprojects);
		}
				
		Date today = new Date();
		context.put("today", Util.toStr(today));
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
		
		//timesheet location
		
		Location office = employee.getOffice();
		
		TimesheetLocation tsLocation = new TimesheetLocation();
		tsLocation.setTimesheet(ts);
		tsLocation.setTimeIn(ts.getTimeIn());
		tsLocation.setTimeOut(ts.getTimeOut());
		tsLocation.setLocation(office);
		
		ts.getLocations().add(tsLocation);
		
		db.save(tsLocation);
		
		db.update(ts);
		
		//return listTimesheets();
		
		context.put("timesheet", ts);
		List<Project> projects = db.list("select p from Project p order by p.name");
		context.put("projects", projects);
		return path + "/timesheet.vm";
		
	}
	
	@Command("editTimesheet")
	public String editTimesheet() {
		Timesheet timesheet = db.find(Timesheet.class, getParam("timesheetId"));
		context.put("timesheet", timesheet);
		List<Project> projects = db.list("select p from Project p order by p.name");
		context.put("projects", projects);
		return path + "/timesheet.vm";
	}
	
	@Command("updateTimesheet")
	public String updateTimesheet() {
		Timesheet ts = db.find(Timesheet.class, getParam("timesheetId"));
		context.put("timesheet", ts);
		
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
		
		//timesheet location
		
		Location office = employee.getOffice();
		
		if ( ts.getLocations().size() == 0 ) {
			TimesheetLocation tsLocation = new TimesheetLocation();
			tsLocation.setTimesheet(ts);
			tsLocation.setTimeIn(ts.getTimeIn());
			tsLocation.setTimeOut(ts.getTimeOut());
			
			if ( tsLocation.getLocation() == null ) {
				tsLocation.setLocation(office);
			}
			
			ts.getLocations().add(tsLocation);
			
			db.save(tsLocation);
			
		}
				
		db.update(ts);
		
		return path + "/timesheet.vm";
	}
	
	@Command("deleteTimesheet")
	public String deleteTimesheet() {
		context.remove("delete_error");
		Timesheet ts = db.find(Timesheet.class, getParam("timesheetId"));
		try {
			List<TimesheetLocation> tsLocations = ts.getLocations();
			db.delete(tsLocations.toArray());
			
			db.delete(ts);
		} catch ( Exception e ) {
			e.printStackTrace();
			context.put("delete_error", e.getMessage());
		}
		return listTimesheets();
	}
	
	@Command("addNewLocation")
	public String addNewLocation() {
		context.remove("timesheetLocation");
		
		Timesheet timesheet = db.find(Timesheet.class, getParam("timesheetId"));
		context.put("timesheet", timesheet);
		
		List<Location> locations = new ArrayList<>();
		locations.add(employee.getOffice());
		
		List<Client> clients = db.list("select c from Client c order by c.name");
		locations.addAll(clients);
		
		context.put("locations", locations);
		
		return path + "/location.vm";
	}
	
	@Command("saveNewLocation")
	public String saveNewLocation() {
		Timesheet timesheet = db.find(Timesheet.class, getParam("timesheetId"));
		context.put("timesheet", timesheet);

		TimesheetLocation tsLocation = new TimesheetLocation();
		
		Location location = db.find(Location.class, getParam("locationId"));
		tsLocation.setTimesheet(timesheet);
		tsLocation.setLocation(location);
		tsLocation.setTimeIn(Util.toTime(getParam("timesheetLocationTimeIn") + " " + getParam("timesheetLocationTimeInAMPM")));
		tsLocation.setTimeOut(Util.toTime(getParam("timesheetLocationTimeOut") + " " + getParam("timesheetLocationTimeOutAMPM")));
		
		db.save(tsLocation);
		
		timesheet.getLocations().add(tsLocation);
		db.update(timesheet);
		
		return path + "/locations.vm";
	}
	
	@Command("editLocation")
	public String editLocation() {
		TimesheetLocation tsLocation = db.find(TimesheetLocation.class, getParam("timesheetLocationId"));
		context.put("timesheetLocation", tsLocation);
		
		List<Location> locations = new ArrayList<>();
		locations.add(employee.getOffice());
		
		List<Client> clients = db.list("select c from Client c order by c.name");
		locations.addAll(clients);
		
		context.put("locations", locations);
		
		return path + "/location.vm";
	}
	
	@Command("updateLocation")
	public String updateLocation() {
		
		TimesheetLocation tsLocation = db.find(TimesheetLocation.class, getParam("timesheetLocationId"));
		
		Timesheet timesheet = tsLocation.getTimesheet();
		context.put("timesheet", timesheet);
		
		Location location = db.find(Location.class, getParam("locationId"));
		tsLocation.setLocation(location);
		tsLocation.setTimeIn(Util.toTime(getParam("timesheetLocationTimeIn") + " " + getParam("timesheetLocationTimeInAMPM")));
		tsLocation.setTimeOut(Util.toTime(getParam("timesheetLocationTimeOut") + " " + getParam("timesheetLocationTimeOutAMPM")));
		
		db.update(tsLocation);
		
		return path + "/locations.vm";
	}
	
	@Command("deleteLocation")
	public String deleteLocation() {
		Timesheet timesheet = db.find(Timesheet.class, getParam("timesheetId"));
		context.put("timesheet", timesheet);
		
		TimesheetLocation tsLocation = db.find(TimesheetLocation.class, getParam("timesheetLocationId"));
		try {
			db.delete(tsLocation);
			
			timesheet.getLocations().remove(tsLocation);
			db.update(timesheet);
			
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		
		
		return path + "/locations.vm";
	}
	
	
	public static void main(String[] args) {
		
		String clientId = "36f65f08a7508054a15179595c110bbfd196b6d1";
		
		Location location = Persistence.db().find(Location.class, clientId);
		System.out.println(location);

		
		String officeId = "ac78f45a9e52235ffe5f8a50f931752a90d507a2";
		location = Persistence.db().find(Location.class, officeId);
		System.out.println(location);
		
	}

}
