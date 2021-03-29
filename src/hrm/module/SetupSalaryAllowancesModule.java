package hrm.module;

import java.util.List;

import hrm.entity.SalaryAllowance;
import lebah.module.LebahUserModule;
import lebah.portal.action.Command;

public class SetupSalaryAllowancesModule extends LebahUserModule {
	
	String path = "apps/setupSalaryAllowances";
	
	@Override
	public String start() {
		listAllowances();
		return path + "/start.vm";
	}
	
	@Command("listAllowances")
	public String listAllowances() {
		List<SalaryAllowance> allowances = db.list("select a from SalaryAllowance a");
		context.put("allowances", allowances);
		
		return path + "/listAllowances.vm";
	}
	
	@Command("addNewAllowance")
	public String addNewAllowance() {
		context.remove("allowance");
		return path + "/allowance.vm";
	}
	
	@Command("saveNewAllowance")
	public String saveNewAllowance() {
		
		SalaryAllowance allowance = new SalaryAllowance();
		allowance.setName(getParam("allowanceName"));
		allowance.setDescription(getParam("allowanceDescription"));
		allowance.setAmount(Util.getDouble(getParam("allowanceAmount")));
		
		db.save(allowance);
		
		return listAllowances();
	}
	
	@Command("editAllowance")
	public String editAllowance() {
		
		SalaryAllowance allowance = db.find(SalaryAllowance.class, getParam("allowanceId"));
		context.put("allowance", allowance);
		
		return path + "/allowance.vm";
	}

	@Command("updateAllowance")
	public String updateAllowance() {
		
		SalaryAllowance allowance = db.find(SalaryAllowance.class, getParam("allowanceId"));
		allowance.setName(getParam("allowanceName"));
		allowance.setDescription(getParam("allowanceDescription"));
		allowance.setAmount(Util.getDouble(getParam("allowanceAmount")));
		
		db.update(allowance);
		
		return listAllowances();
	}
	
	@Command("deleteAllowance")
	public String deleteAllowance() {
		
		context.remove("delete_error");
		SalaryAllowance allowance = db.find(SalaryAllowance.class, getParam("allowanceId"));
		try {
			db.delete(allowance);
			
		} catch ( Exception e ) {
			context.put("delete_error", "Constraint violation... can't delete!");
		}
		
		return listAllowances();
	}

}
