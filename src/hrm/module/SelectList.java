package hrm.module;

import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.VelocityContext;

import hrm.entity.Address;
import hrm.entity.Company;
import hrm.entity.CompanyHQ;
import hrm.entity.Department;
import hrm.entity.District;
import hrm.entity.Job;
import hrm.entity.SalaryAllowance;
import hrm.entity.SalaryDeductionItem;
import hrm.entity.State;
import lebah.db.entity.Persistence;

public class SelectList {
	
	
	public static void listStates(VelocityContext context, String countryId) {
		List<State> states = Persistence.db().list("select s from State s where s.country.id = '" + countryId + "' order by s.id");
		context.put("states", states);
		
	}
	
	public static void listDistricts(VelocityContext context, String stateId) {
		List<District> districts = Persistence.db().list("select d from District d where d.state.id = '" + stateId + "' order by d.id");
		context.put("districts", districts);
	}
	
	public static void listDistricts(VelocityContext context, Address address) {
		if ( address != null && address.getDistrict() != null && address.getDistrict().getState() != null) {
			List<District> districts = Persistence.db().list("select d from District d where d.state.id = '" + address.getDistrict().getState().getId() + "' order by d.id");
			context.put("districts", districts);
		} else {
			context.remove("districts");
		}
	}
	
	public static void listCompanies(VelocityContext context) {
		List<Company> companies = new ArrayList<>();
		CompanyHQ companyHq = Persistence.db().find(CompanyHQ.class, "HQ");
		companies.add(companyHq);
		
		List<Company> subs = Persistence.db().list("select c from Company c where c.parent.id = '" + companyHq.getId() + "'");
		companies.addAll(subs);
		
		context.put("companies", companies);
		
	}
	
	public static void listDepartments(VelocityContext context, String companyId) {
		List<Department> departments = Persistence.db().list("select d from Department d where d.company.id = '" + companyId + "'");
		context.put("departments", departments);
	}
	
	public static void listJobs(VelocityContext context) {
		List<Job> jobs = Persistence.db().list("select j from Job j order by j.jobLevel.levelOrder");
		context.put("jobs", jobs);
	}
	
	public static void listSalaryDeductionItems(VelocityContext context) {
		List<SalaryDeductionItem> items = Persistence.db().list("select i from SalaryDeductionItem i");
		context.put("salaryDeductionItems", items);
	}
	
	public static void listSalaryAllowances(VelocityContext context) {
		List<SalaryAllowance> salaryAllowances = Persistence.db().list("select a from SalaryAllowance a");
		context.put("salaryAllowances", salaryAllowances);
	}

}
