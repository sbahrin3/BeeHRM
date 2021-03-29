package hrm.module;

import java.util.List;

import hrm.entity.SalaryDeductionItem;
import lebah.module.LebahUserModule;
import lebah.portal.action.Command;

public class SetupSalaryDeductionsModule extends LebahUserModule {
	
	String path = "apps/setupSalaryDeductions";
	
	@Override
	public String start() {
		listDeductions();
		return path + "/start.vm";
	}
	
	@Command("listDeductions")
	public String listDeductions() {
		List<SalaryDeductionItem> deductions = db.list("select i from SalaryDeductionItem i");
		context.put("deductions", deductions);
		return path + "/listDeductions.vm";
	}
	
	@Command("addNewDeduction")
	public String addNewDeduction() {
		context.remove("deduction");
		return path + "/deduction.vm";
	}
	
	@Command("saveNewDeduction")
	public String saveNewDeduction() {
		
		SalaryDeductionItem deduction = new SalaryDeductionItem();
		deduction.setName(getParam("deductionName"));
		deduction.setDescription(getParam("deductionDescription"));
		deduction.setUseRate(getParam("isUseRate").equals("1"));
		deduction.setRate(Util.getInt(getParam("deductionRate")));
		deduction.setAmount(Util.getDouble(getParam("deductionAmount")));
		deduction.setRateOnBasicOnly(getParam("isRateOnBasicOnly").equals("1"));
		
		db.save(deduction);
		
		return listDeductions();
	}
	
	@Command("editDeduction")
	public String editDeduction() {
		
		SalaryDeductionItem deduction = db.find(SalaryDeductionItem.class, getParam("deductionId"));
		context.put("deduction", deduction);
		
		return path + "/deduction.vm";
	}

	@Command("updateDeduction")
	public String updateDeduction() {
		
		SalaryDeductionItem deduction = db.find(SalaryDeductionItem.class, getParam("deductionId"));
		deduction.setName(getParam("deductionName"));
		deduction.setDescription(getParam("deductionDescription"));
		deduction.setUseRate(getParam("isUseRate").equals("1"));
		deduction.setRate(Util.getInt(getParam("deductionRate")));
		deduction.setAmount(Util.getDouble(getParam("deductionAmount")));
		deduction.setRateOnBasicOnly(getParam("isRateOnBasicOnly").equals("1"));
		
		db.update(deduction);
		
		return listDeductions();
	}
	
	@Command("deleteDeduction")
	public String deleteDeduction() {
		
		context.remove("delete_error");
		SalaryDeductionItem deduction = db.find(SalaryDeductionItem.class, getParam("deductionId"));
		try {
			db.delete(deduction);
			
		} catch ( Exception e ) {
			context.put("delete_error", "Constraint violation... can't delete!");
		}
		
		return listDeductions();
	}
}
