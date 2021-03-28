package hrm.module;

import java.util.List;

import hrm.entity.Address;
import hrm.entity.Company;
import hrm.entity.CompanyHQ;
import hrm.entity.Department;
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
	
	@Command("selectDistricts")
	public String selectDistricts() {
		SelectList.listDistricts(context, getParam("stateId"));
		return path + "/selectDistricts.vm";
	}
	
	@Command("addNewOffice")
	public String addNewOffice() {
		
		Company company = db.find(Company.class, getParam("companyId"));
		context.put("company", company);
		context.remove("office");
		context.remove("districts");
		
		SelectList.listStates(context, "MAS");
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
		
		SelectList.listStates(context, "MAS");
		SelectList.listDistricts(context, office.getAddress());
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
		
		SelectList.listStates(context, "MAS");
		return path + "/listOffices.vm";
	}
	
	@Command("deleteOffice")
	public String deleteOffice() {
		
		context.remove("delete_error");
		
		Office office = db.find(Office.class, getParam("officeId"));
		Company company = office.getCompany();
		context.put("company", company);
		
		try {
			db.delete(office);
			company.getOffices().remove(office);
			db.update(company);
		} catch ( Exception e ) {
			context.put("delete_error", "Constraint violation... can't delete!");
		}

		
		return path + "/listOffices.vm";
	}
	
	@Command("listOffices")
	public String listOffices() {
		Company company = db.find(Company.class, getParam("companyId"));
		context.put("company", company);
		return path + "/listOffices.vm";
	}
	
	@Command("listDepartments")
	public String listDepartments() {
		Company company = db.find(Company.class, getParam("companyId"));
		context.put("company", company);
		return path + "/listDepartments.vm";
	}
	
	@Command("addNewDepartment")
	public String addNewDepartment() {
		Company company = db.find(Company.class, getParam("companyId"));
		context.put("company", company);
		context.remove("department");
		return path + "/department.vm";
	}
	
	@Command("saveNewDepartment")
	public String saveNewDepartment() {
		Company company = db.find(Company.class, getParam("companyId"));
		context.put("company", company);
		
		Department department = new Department();
		department.setCompany(company);
		department.setName(getParam("departmentName"));
		
		db.save(department);
		
		company.getDepartments().add(department);
		db.update(company);
		
		return path + "/listDepartments.vm";
	}
	
	@Command("editDepartment")
	public String editDepartment() {
		Department department = db.find(Department.class, getParam("departmentId"));
		context.put("department", department);
		context.put("company", department.getCompany());
		
		return path + "/department.vm";
	}
	
	@Command("updateDepartment")
	public String updateDepartment() {
		
		Department department = db.find(Department.class, getParam("departmentId"));
		context.put("department", department);
		
		department.setName(getParam("departmentName"));
		db.update(department);
		
		Company company = department.getCompany();
		context.put("company", company);
		
		return path + "/listDepartments.vm";
	}
	
	@Command("deleteDepartment")
	public String deleteDepartment() {
		
		context.remove("delete_error");
		
		Department department = db.find(Department.class, getParam("departmentId"));
		Company company = department.getCompany();
		context.put("company", company);
		
		try {
			db.delete(department);
		
			company.getDepartments().remove(department);
			db.update(company);
		} catch ( Exception e ) {
			context.put("delete_error", "Constraint violation... can't delete!");
		}

		return path + "/listDepartments.vm";
	}

}
