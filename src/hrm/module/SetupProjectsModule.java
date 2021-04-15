package hrm.module;

import java.util.List;

import hrm.entity.Project;
import lebah.module.LebahUserModule;
import lebah.portal.action.Command;

public class SetupProjectsModule extends LebahUserModule {
	
	String path = "apps/setupProjects";
	
	@Override
	public String start() {
		listProjects();
		return path + "/start.vm";
	}
	
	@Command("listProjects")
	public String listProjects() {
		List<Project> projects = db.list("select p from Project p order by p.fromDate desc");
		context.put("projects", projects);
		return path + "/listProjects.vm";
	}
	
	@Command("addNewProject")
	public String addNewProject() {
		context.remove("project");
		return path + "/project.vm";
	}
	
	@Command("saveNewProject")
	public String saveNewProject() {
		Project project = new Project();
		project.setName(getParam("projectName"));
		project.setDescription(getParam("projectDescription"));
		project.setToDate(Util.toDate(getParam("projectToDate")));
		project.setFromDate(Util.toDate(getParam("projectFromDate")));
		db.save(project);
		
		return listProjects();
	}
	
	@Command("editProject")
	public String editProject() {
		Project project = db.find(Project.class, getParam("projectId"));
		context.put("project", project);
		return path + "/project.vm";
	}
	
	@Command("updateProject")
	public String updateProject() {
		Project project = db.find(Project.class, getParam("projectId"));
		project.setName(getParam("projectName"));
		project.setDescription(getParam("projectDescription"));
		project.setToDate(Util.toDate(getParam("projectToDate")));
		project.setFromDate(Util.toDate(getParam("projectFromDate")));
		db.update(project);
		return listProjects();
	}
	
	@Command("deleteProject")
	public String deleteProject() {
		context.remove("delete_error");
		Project project = db.find(Project.class, getParam("projectId"));
		try {
			db.delete(project);
		} catch ( Exception e ) {
			context.put("delete_error", "Can't delete project! " + e.getMessage());
		}
		return listProjects();
	}

}
