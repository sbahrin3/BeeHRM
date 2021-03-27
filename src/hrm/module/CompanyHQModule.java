package hrm.module;

import java.util.List;

import hrm.entity.Company;
import hrm.entity.CompanyHQ;
import hrm.entity.District;
import hrm.entity.Office;
import hrm.entity.State;
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
	
	private void listStates(String countryId) {
		List<State> states = db.list("select s from State s where s.country.id = '" + countryId + "' order by s.id");
		context.put("states", states);
		
	}
	
	private void listDistricts(String stateId) {
		List<District> districts = db.list("select d from District d where d.state.id = '" + stateId + "' order by d.id");
		context.put("districts", districts);
	}
	
	@Command("selectDistricts")
	public String selectDistricts() {
		listDistricts(getParam("stateId"));
		return path + "/selectDistricts.vm";
	}
	
	@Command("addNewOffice")
	public String addNewOffice() {
		
		Company company = db.find(Company.class, getParam("companyId"));
		context.put("company", company);
		context.remove("office");
		
		listStates("MAS");
		return path + "/office.vm";
	}
	
	@Command("saveNewOffice")
	public String saveNewOffice() {
		
		District district = db.find(District.class, getParam("districtId"));
		
		Company company = db.find(Company.class, getParam("companyId"));
		context.put("company", company);
		
		Office office = new Office();
		office.setCompany(company);
		office.setName(getParam("officeName"));
		office.setPrincipal(getParam("isPrincipal").equals("yes"));
		office.getAddress().setAddress1(getParam("address1"));
		office.getAddress().setAddress2(getParam("address2"));
		office.getAddress().setPostcode(getParam("postcode"));
		office.getAddress().setDistrict(district);
		
		db.save(office);
		
		company.getOffices().add(office);
		db.update(company);
		
		return path + "/listOffices.vm";
	}
	
	@Command("editOffice")
	public String editOffice() {
		
		Office office = db.find(Office.class, getParam("officeId"));
		context.put("office", office);
		context.put("company", office.getCompany());
		
		listStates("MAS");
		return path + "/office.vm";
	}
	
	@Command("updateOffice")
	public String updateOffice() {
		
		District district = db.find(District.class, getParam("districtId"));
		
		Office office = db.find(Office.class, getParam("officeId"));
		context.put("office", office);
		context.put("company", office.getCompany());
		office.setName(getParam("officeName"));
		office.setPrincipal(getParam("isPrincipal").equals("yes"));
		office.getAddress().setAddress1(getParam("address1"));
		office.getAddress().setAddress2(getParam("address2"));
		office.getAddress().setPostcode(getParam("postcode"));
		office.getAddress().setDistrict(district);
		
		db.update(office);
		
		listStates("MAS");
		return path + "/listOffices.vm";
	}
	
	

}
