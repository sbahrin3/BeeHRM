package hrm.module;

import java.util.List;

import hrm.entity.SalaryAllowance;
import hrm.entity.SalaryConfig;
import hrm.entity.SalaryDeductionItem;
import lebah.db.entity.Persistence;
import lebah.module.LebahUserModule;
import lebah.portal.action.Command;

public class SetupSalaryConfigModule extends LebahUserModule {
	
	String path = "/apps/setupSalaryConfig";
	
	@Override
	public String start() {
		listSalaryConfigs();
		return path + "/start.vm";
	}
	
	@Command("listSalaryConfigs")
	public String listSalaryConfigs() {
		
		List<SalaryConfig> salaryConfigs = db.list("select c from SalaryConfig c");
		context.put("salaryConfigs", salaryConfigs);
		
		return path + "/listSalaryConfigs.vm";
	}
	
	@Command("addNewSalaryConfig")
	public String addNewSalaryConfig() {
		context.remove("salaryConfig");

		return path + "/salaryConfig.vm";
	}
	
	@Command("saveNewSalaryConfig")
	public String saveNewSalaryConfig() {
		
		SalaryConfig salaryConfig = new SalaryConfig();
		salaryConfig.setCode(getParam("salaryConfigCode"));
		salaryConfig.setDescription(getParam("salaryConfigDescription"));
		db.save(salaryConfig);
		
		context.put("salaryConfig", salaryConfig);

		return path + "/salaryConfig.vm";
	}
	
	@Command("editSalaryConfig")
	public String editSalaryConfig() {
		SalaryConfig salaryConfig = db.find(SalaryConfig.class, getParam("salaryConfigId"));
		context.put("salaryConfig", salaryConfig);
	
		return path + "/salaryConfig.vm";
	}
	
	@Command("updateSalaryConfig")
	public String updateSalaryConfig() {
		SalaryConfig salaryConfig = db.find(SalaryConfig.class, getParam("salaryConfigId"));
		context.put("salaryConfig", salaryConfig);
		
		salaryConfig.setCode(getParam("salaryConfigCode"));
		salaryConfig.setDescription(getParam("salaryConfigDescription"));
		
		db.update(salaryConfig);
				
		return path + "/salaryConfig.vm";	
	}

	@Command("deleteSalaryConfig")
	public String deleteSalaryConfig() {
		context.remove("delete_error");
		SalaryConfig salaryConfig = db.find(SalaryConfig.class, getParam("salaryConfigId"));
		try {
			db.delete(salaryConfig);
		} catch ( Exception e ) {
			context.put("delete_error", "Constraint violation... can't delete!");
		}
		
		return listSalaryConfigs();
	}
	
	
	
	//SALARY ALLOWANCES SETUP
	
	@Command("selectSalaryAllowances")
	public String selectSalaryAllowances() {
		SalaryConfig salaryConfig = db.find(SalaryConfig.class, getParam("salaryConfigId"));
		context.put("salaryConfig", salaryConfig);
		
		List<SalaryAllowance> items = Persistence.db().list("select i from SalaryAllowance i");
		context.put("allowances", items);
		
		return path + "/listSelectAllowances.vm";
	}
	
	@Command("listSalaryAllowances")
	public String listSalaryAllowances() {
		SalaryConfig salaryConfig = db.find(SalaryConfig.class, getParam("salaryConfigId"));
		context.put("salaryConfig", salaryConfig);
		return path + "/listAllowances.vm";
	}
	
	@Command("saveSelectAllowances")
	public String saveSelectAllowances() {
		SalaryConfig salaryConfig = db.find(SalaryConfig.class, getParam("salaryConfigId"));
		context.put("salaryConfig", salaryConfig);
		
		String[] allowanceIds = request.getParameterValues("allowanceIds");
		for ( String allowanceId : allowanceIds ) {
			SalaryAllowance item = db.find(SalaryAllowance.class, allowanceId);
			if ( !salaryConfig.getAllowances().contains(item) ) {
				salaryConfig.getAllowances().add(item);
			}
		}
		
		db.update(salaryConfig);
		
		return path + "/listAllowances.vm";
	}
	
	@Command("removeAllowance")
	public String removeAllowance() {
		SalaryConfig salaryConfig = db.find(SalaryConfig.class, getParam("salaryConfigId"));
		context.put("salaryConfig", salaryConfig);
		
		SalaryAllowance allowance = db.find(SalaryAllowance.class, getParam("allowanceId"));
		salaryConfig.getAllowances().remove(allowance);
		
		db.update(salaryConfig);
		
		return path + "/listAllowances.vm";
	}
	
	
	//SALARY DEDUCTIONS SETUP
	
	@Command("selectSalaryDeductions")
	public String selectSalaryDeductions() {
		SalaryConfig salaryConfig = db.find(SalaryConfig.class, getParam("salaryConfigId"));
		context.put("salaryConfig", salaryConfig);
		
		List<SalaryDeductionItem> items = Persistence.db().list("select i from SalaryDeductionItem i");
		context.put("deductions", items);
		
		return path + "/listSelectDeductions.vm";
	}
	
	@Command("listSalaryDeductions")
	public String listSalaryDeductions() {
		SalaryConfig salaryConfig = db.find(SalaryConfig.class, getParam("salaryConfigId"));
		context.put("salaryConfig", salaryConfig);
		return path + "/listDeductions.vm";
	}
	
	@Command("saveSelectDeductions")
	public String saveSelectDeductions() {
		SalaryConfig salaryConfig = db.find(SalaryConfig.class, getParam("salaryConfigId"));
		context.put("salaryConfig", salaryConfig);
		
		String[] deductionIds = request.getParameterValues("deductionIds");
		for ( String deductionId : deductionIds ) {
			SalaryDeductionItem item = db.find(SalaryDeductionItem.class, deductionId);
			if ( !salaryConfig.getDeductions().contains(item) ) {
				salaryConfig.getDeductions().add(item);
			}
		}
		
		db.update(salaryConfig);
		
		return path + "/listDeductions.vm";
	}
	
	@Command("removeDeduction")
	public String removeDeduction() {
		SalaryConfig salaryConfig = db.find(SalaryConfig.class, getParam("salaryConfigId"));
		context.put("salaryConfig", salaryConfig);
		
		SalaryDeductionItem deduction = db.find(SalaryDeductionItem.class, getParam("deductionId"));
		salaryConfig.getDeductions().remove(deduction);
		
		db.update(salaryConfig);
		
		return path + "/listDeductions.vm";
	}
}
