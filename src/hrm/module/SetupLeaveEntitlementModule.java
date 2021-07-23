package hrm.module;

import java.util.List;

import hrm.entity.Leave;
import hrm.entity.LeaveEntitlement;
import hrm.entity.LeaveEntitlementItem;
import lebah.module.LebahUserModule;
import lebah.portal.action.Command;

public class SetupLeaveEntitlementModule extends LebahUserModule {
	
	String path = "apps/setupLeaveEntitlement";
	
	@Override
	public String start() {
		listLeaveEntitlements();
		return path + "/start.vm";
	}

	@Command("listLeaveEntitlements")
	public String listLeaveEntitlements() {
		List<LeaveEntitlement> leaveEntitlements = db.list("select l from LeaveEntitlement l");
		context.put("leaveEntitlements", leaveEntitlements);
		return path + "/listLeaveEntitlements.vm";
	}
	
	@Command("addNewLeaveEntitlement")
	public String addNewLeaveEntitlement() {
		context.remove("leaveEntitlement");
		return path + "/leaveEntitlement.vm";
	}
	
	@Command("saveNewLeaveEntitlement")
	public String saveNewLeaveEntitlement() {
		LeaveEntitlement leaveEntitlement = new LeaveEntitlement();
		leaveEntitlement.setCode(getParam("leaveEntitlementCode"));
		leaveEntitlement.setName(getParam("leaveEntitlementName"));
		leaveEntitlement.setDescription(getParam("leaveEntitlementDescription"));
		
		db.save(leaveEntitlement);
		context.put("leaveEntitlement", leaveEntitlement);
		
		return path + "/leaveEntitlement.vm";
	}
	
	@Command("editLeaveEntitlement")
	public String editLeaveEntitlement() {
		LeaveEntitlement leaveEntitlement = db.find(LeaveEntitlement.class, getParam("leaveEntitlementId"));
		context.put("leaveEntitlement", leaveEntitlement);
		
		return path + "/leaveEntitlement.vm";
	}
	
	@Command("updateLeaveEntitlement")
	public String updateLeaveEntitlement() {
		LeaveEntitlement leaveEntitlement = db.find(LeaveEntitlement.class, getParam("leaveEntitlementId"));
		context.put("leaveEntitlement", leaveEntitlement);
		
		leaveEntitlement.setCode(getParam("leaveEntitlementCode"));
		leaveEntitlement.setName(getParam("leaveEntitlementName"));
		leaveEntitlement.setDescription(getParam("leaveEntitlementDescription"));
		
		db.update(leaveEntitlement);
		
		
		
		return path + "/leaveEntitlement.vm";
	}
	
	@Command("deleteLeaveEntitlement")
	public String deleteLeaveEntitlement() {
		context.remove("delete_error");
		LeaveEntitlement leaveEntitlement = db.find(LeaveEntitlement.class, getParam("leaveEntitlementId"));
		try {
			db.delete(leaveEntitlement);
		} catch ( Exception e ) {
			context.put("delete_error", "Constraint violation... can't delete!");
		}
		
		return listLeaveEntitlements();
	}
	
	
	@Command("addNewLeaveEntitlementItem")
	public String addNewLeaveEntitlementItem() {
		context.remove("leaveEntitlementItem");
		List<Leave> leaves = db.list("select l from Leave l order by l.orderNo");
		context.put("leaves", leaves);
		
		return path + "/leaveEntitlementItem.vm";
	}
	
	@Command("saveNewLeaveEntitlementItem")
	public String saveNewLeaveEntitlementItem() {
		
		LeaveEntitlement leaveEntitlement = db.find(LeaveEntitlement.class, getParam("leaveEntitlementId"));
		context.put("leaveEntitlement", leaveEntitlement);
		
		Leave leave = db.find(Leave.class, getParam("leaveId"));
		
		LeaveEntitlementItem item = new LeaveEntitlementItem();
		item.setLeave(leave);
		item.setLeaveEntitlement(leaveEntitlement);
		item.setNumberOfDays(Util.getInt(getParam("numberOfDays")));
		
		db.save(item);
		
		leaveEntitlement.getItems().add(item);
		db.update(leaveEntitlement);
		
		
		return path + "/listLeaveEntitlementItems.vm";
	}
	
	@Command("deleteLeaveEntitlementItem")
	public String deleteLeaveEntitlementItem() {
		
		LeaveEntitlement leaveEntitlement = db.find(LeaveEntitlement.class, getParam("leaveEntitlementId"));
		context.put("leaveEntitlement", leaveEntitlement);
		
		LeaveEntitlementItem item = db.find(LeaveEntitlementItem.class, getParam("leaveEntitlementItemId"));
		try {
			db.delete(item);
			leaveEntitlement.getItems().remove(item);
			db.update(leaveEntitlement);
		} catch ( Exception e ) {
			e.printStackTrace();
		}
			
		
		return path + "/listLeaveEntitlementItems.vm";
	}
	
}
