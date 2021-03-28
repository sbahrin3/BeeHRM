package hrm.module;

import java.util.List;

import hrm.entity.JobLevel;
import lebah.module.LebahUserModule;
import lebah.portal.action.Command;

public class SetupJobLevelModule extends LebahUserModule {
	
	String path = "apps/setupJobLevel";
	
	@Override
	public String start() {
		
		listJobLevels();
		
		return path + "/start.vm";
	}
	
	@Command("listJobLevels")
	public String listJobLevels() {
		
		List<JobLevel> jobLevels = db.list("select j from JobLevel j order by j.levelOrder");
		context.put("jobLevels", jobLevels);
		
		return path + "/listJobLevels.vm";
	}
	
	@Command("addNewJobLevel")
	public String addNewJobLevel() {
		context.remove("jobLevel");
		return path + "/jobLevel.vm";
	}
	
	@Command("saveNewJobLevel")
	public String saveNewJobLevel() {
		
		JobLevel jobLevel = new JobLevel();
		jobLevel.setCode(getParam("jobLevelCode"));
		jobLevel.setName(getParam("jobLevelName"));
		jobLevel.setLevelOrder(Integer.parseInt(getParam("jobLevelOrder")));
		
		db.save(jobLevel);
		
		return listJobLevels();
	}
	
	@Command("editJobLevel")
	public String editJobLevel() {
		
		JobLevel jobLevel = db.find(JobLevel.class, getParam("jobLevelId"));
		context.put("jobLevel", jobLevel);
		
		return path + "/jobLevel.vm";
	}
	
	@Command("updateJobLevel")
	public String updateJobLevel() {
		
		JobLevel jobLevel = db.find(JobLevel.class, getParam("jobLevelId"));
		jobLevel.setCode(getParam("jobLevelCode"));
		jobLevel.setName(getParam("jobLevelName"));
		jobLevel.setLevelOrder(Integer.parseInt(getParam("jobLevelOrder")));
		
		db.update(jobLevel);
		
		return listJobLevels();
	}
	
	@Command("deleteJobLevel")
	public String deleteJobLevel() {
		
		context.remove("delete_error");
		
		JobLevel jobLevel = db.find(JobLevel.class, getParam("jobLevelId"));
		try {
			db.delete(jobLevel);
		} catch ( Exception e ) {
			context.put("delete_error", "Constraint violation... can't delete!");
		}
		
		return listJobLevels();
	}

}
