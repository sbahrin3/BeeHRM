package hrm.module;

import java.util.List;

import hrm.entity.Company;
import hrm.entity.CompanyHQ;
import hrm.entity.CompanySubsidiary;
import lebah.portal.action.Command;

public class CompanySubsidiariesModule extends CompanyHQModule {
	
		
	@Override
	public String start() {
		listCompanies();
		return path + "/start2.vm";
	}
	
	private Company getCompanyHq() {
		
		CompanyHQ companyHq = db.find(CompanyHQ.class, "HQ");
		if ( companyHq == null ) {
			companyHq = new CompanyHQ();
			companyHq.setId("HQ");
			companyHq.setName("Company Name");
			companyHq.setRegistrationNumber("RN0123456");
			db.save(companyHq);
		}
		return companyHq;
		
	}
	
	@Command("listCompanies")
	public String listCompanies() {
		Company companyHq = getCompanyHq();
		List<Company> companies = db.list("select c from Company c where c.parent.id = '" + companyHq.getId() + "' order by c.name");
		context.put("companies", companies);
		
		return path + "/listCompanies.vm";
	}
	
	@Command("addNewCompany")
	public String addNewCompany() {
		context.remove("company");
		return path + "/company.vm";
	}
		
	@Command("saveNewCompany")
	public String saveNewCompany() {
		Company companyHq = getCompanyHq();
		
		Company company = new CompanySubsidiary();
		company.setParent(companyHq);
		company.setName(getParam("companyName"));
		company.setRegistrationNumber(getParam("companyRegistrationNumber"));
		
		db.save(company);
		context.put("company", company);
		
		companyHq.getBranches().add(company);
		db.update(companyHq);
		
		List<Company> companies = db.list("select c from Company c where c.parent.id = '" + companyHq.getId() + "' order by c.name");
		context.put("companies", companies);
		return path + "/listCompanies.vm";
	}
	
	@Command("editCompany")
	public String editCompany() {
		Company company = db.find(Company.class, getParam("companyId"));
		context.put("company", company);
		return path + "/company.vm";
	}

	@Command("updateCompany")
	public String updateCompany() {
		
		Company company = db.find(Company.class, getParam("companyId"));
		company.setName(getParam("companyName"));
		company.setRegistrationNumber(getParam("companyRegistrationNumber"));
		
		db.update(company);
		
		List<Company> companies = db.list("select c from Company c where c.parent.id = '" + company.getParent().getId() + "' order by c.name");
		context.put("companies", companies);
		return path + "/listCompanies.vm";
	}
	
	@Command("deleteCompany")
	public String deleteCompany() {
		
		context.remove("delete_error");
		
		Company company = db.find(Company.class, getParam("companyId"));
		Company parent = company.getParent();
		try {
			db.delete(company);
			parent.getBranches().remove(company);
			db.update(parent);
	
		} catch ( Exception e ) {
			context.put("delete_error", "Constraint violation... can't delete!");
		}
		
		List<Company> companies = db.list("select c from Company c where c.parent.id = '" + parent.getId() + "' order by c.name");
		context.put("companies", companies);
		
		return path + "/listCompanies.vm";
	}
}
