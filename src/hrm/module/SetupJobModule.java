package hrm.module;

import java.util.List;

import hrm.entity.Job;
import hrm.entity.JobLevel;
import lebah.module.LebahUserModule;
import lebah.portal.action.Command;

public class SetupJobModule extends LebahUserModule {
	
	String path = "apps/setupJob";
	
	@Override
	public String start() {
		
		listJobs();
		
		return path + "/start.vm";
	}
	
	private void selectJobLevels() {
		List<JobLevel> jobLevels = db.list("select l from JobLevel l order by l.levelOrder");
		context.put("jobLevels", jobLevels);
	}
	
	@Command("listJobs")
	public String listJobs() {
		
		List<Job> jobs = db.list("select j from Job j order by j.jobLevel.levelOrder");
		return path + "/listJobs.vm";
	}
	
	@Command("addNewJob")
	public String addNewJob() {
		
		context.remove("job");
		selectJobLevels();
		
		return path + "/job.vm";
	}
	
	@Command("saveNewJob")
	public String saveNewJob() {
		
		JobLevel jobLevel = db.find(JobLevel.class, getParam("jobLevelId"));
		
		Job job = new Job();
		job.setJobLevel(jobLevel);
		job.setName(getParam("jobName"));
		
		db.save(job);
		
		return listJobs();
	}
	
	@Command("editJob")
	public String editJob() {
		
		Job job = db.find(Job.class, getParam("jobId"));
		context.put("job", job);
		
		selectJobLevels();
		
		return path + "/job.vm";
	}
	
	@Command("updateJob")
	public String updateJob() {
		
		JobLevel jobLevel = db.find(JobLevel.class, getParam("jobLevelId"));
		
		Job job = db.find(Job.class, getParam("jobId"));
		job.setJobLevel(jobLevel);
		job.setName(getParam("jobName"));
		
		db.update(job);
		
		return listJobs();
	}
	
	@Command("deleteJob")
	public String deleteJob() {
		
		context.remove("delete_error");
		
		Job job = db.find(Job.class, getParam("jobId"));
		try {
			db.delete(job);
		} catch ( Exception e ) {
			context.put("delete_error", "Constraint violation... can't delete!");
		}
		
		return listJobs();
	}

}
