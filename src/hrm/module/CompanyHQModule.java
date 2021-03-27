package hrm.module;

import hrm.entity.Company;
import hrm.entity.CompanyHQ;
import hrm.entity.Office;
import lebah.module.LebahUserModule;
import lebah.portal.action.Command;

/**
 * 
 * @author shamsulbahrin
 *
 */
public class CompanyHQModule extends LebahUserModule {
	
	
	String path = "apps/companyHQ";
	
	@Override
	public String start() {
		
		getCompanyHQ();
				
		return path + "/start.vm";
	}
	
	@Command("getCompanyHQ")
	public String getCompanyHQ() {
		
		CompanyHQ companyHq = db.find(CompanyHQ.class, "HQ");
		if ( companyHq == null ) {
			companyHq = new CompanyHQ();
			companyHq.setId("HQ");
			companyHq.setName("Company Name");
			companyHq.setRegistrationNumber("RN0123456");

			db.save(companyHq);
		}
		context.put("company", companyHq);
		
		return path + "/companyHQ.vm";
	}
	
	@Command("saveCompanyHQ")
	public String saveCompanyHQ() {
		
		CompanyHQ companyHq = db.find(CompanyHQ.class, "HQ");
		companyHq.setName(getParam("companyName"));
		companyHq.setRegistrationNumber(getParam("companyRegistrationNumber"));
		
		db.update(companyHq);
		
		context.put("company", companyHq);
		
		return path + "/companyHQ.vm";
	}
	
	@Command("addNewOffice")
	public String addNewOffice() {
		
		Company company = db.find(Company.class, getParam("companyId"));
		context.put("company", company);
		
		return path + "/office.vm";
	}
	
	@Command("saveNewOffice")
	public String saveNewOffice() {
		
		Company company = db.find(Company.class, getParam("companyId"));
		context.put("company", company);
		
		Office office = new Office();
		office.setCompany(company);
		office.setName(getParam("officeName"));
		office.setPrincipal(getParam("isPrincipal").equals("yes"));
		
		db.save(office);
		
		company.getOffices().add(office);
		db.update(company);
		
		return path + "/listOffices.vm";
	}

}
