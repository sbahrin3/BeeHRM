package hrm.module;

import java.util.List;

import hrm.entity.Leave;
import lebah.module.LebahUserModule;
import lebah.portal.action.Command;

public class SetupLeaveModule extends LebahUserModule {
	
	String path = "apps/setupLeave";

	@Override
	public String start() {
		listLeaves();
		return path + "/start.vm";
	}
	
	@Command("listLeaves")
	public String listLeaves() {
		
		List<Leave> leaves = db.list("select l from Leave l order by orderNo");
		context.put("leaves", leaves);
		return path + "/listLeaves.vm";
	}
	
	@Command("addNewLeave")
	public String addNewLeave() {
		context.remove("leave");
		return path + "/leave.vm";
	}

	
	@Command("saveNewLeave")
	public String saveNewLeave() {
		Leave leave = new Leave();
		leave.setCode(getParam("leaveCode"));
		leave.setName(getParam("leaveName"));
		leave.setOrderNo(Util.getInt(getParam("leaveOrderNo")));
		leave.setPaid("1".equals(getParam("isPaid")));
		
		db.save(leave);
		
		return listLeaves();
	}
	
	@Command("editLeave")
	public String editLeave() {
		Leave leave = db.find(Leave.class, getParam("leaveId"));
		context.put("leave", leave);
		return path + "/leave.vm";
	}
	
	@Command("updateLeave")
	public String updateLeave() {
		Leave leave = db.find(Leave.class, getParam("leaveId"));

		leave.setCode(getParam("leaveCode"));
		leave.setName(getParam("leaveName"));
		leave.setOrderNo(Util.getInt(getParam("leaveOrderNo")));
		leave.setPaid("1".equals(getParam("isPaid")));
		
		return listLeaves();
	}
	
	@Command("deleteLeave")
	public String deleteLeave() {
		context.remove("delete_error");
		Leave leave = db.find(Leave.class, getParam("leaveId"));
		try {
			db.delete(leave);
		} catch ( Exception e ) {
			context.put("delete_error", "Constraint violation... cant' delete!");
		}
		return listLeaves();
	}
}
