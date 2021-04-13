package hrm.setup;


import java.util.ArrayList;
import java.util.List;

import lebah.db.entity.Menu;
import lebah.db.entity.Persistence;
import lebah.db.entity.Role;

public class CreateHRMenus {
	
	public static void main(String[] args) {
		run();
	}
	
	public static void run() {
		
		Persistence db = Persistence.db();
				
		db.execute("delete from Menu m where m.parent is not null");
		db.execute("delete from Menu m");
		
		String[][] parents = {
				{"company","Company"},
				{"administration","Administration"},
				{"employees","Employees"}
		};
		
		List<Menu> parentMenus = new ArrayList<>();
		int i = 0;
		for ( String[] item : parents ) {
			i++;
			Menu menu = new Menu();
			menu.setGrouped(1);
			menu.setId(item[0]);
			menu.setTitle(item[1]);
			menu.setOrderNo(i);
			menu.setIcon("fa fa-square");
			parentMenus.add(menu);
		}
		
		db.save(parentMenus.toArray());
		
		
		String[][] childs = {{"company", "holding_company","Holding Company", "hrm.module.CompanyHQModule"},
				{"company","company_subsidiaries","Subsidiaries Company","hrm.module.CompanySubsidiariesModule"},
				{"administration","job_levels","Job Levels","hrm.module.SetupJobLevelModule"},
				{"administration","job_positions","Job Titles","hrm.module.SetupJobModule"},
				{"administration","salary_allowances","Salary Allowances","hrm.module.SetupSalaryAllowancesModule"},
				{"administration","salary_deductions","Salary Deductions","hrm.module.SetupSalaryDeductionsModule"},
				{"administration","salary_entitlements","Salary Entitlements","hrm.module.SetupSalaryConfigModule"},
				{"administration","leave_types","Leave Types","hrm.module.SetupLeaveModule"},
				{"administration","leave_entitlements","Leave Entitlements","hrm.module.SetupLeaveEntitlementModule"},
				{"administration","events_calendar","Holidays Calendar","hrm.module.EventCalendarModule"},
				{"administration","weekends_states","Weekends by States","hrm.module.SetupWeekendTypeByStatesModule"},
				{"employees","employees_records","Employees Records","hrm.module.ManageEmployeesModule"},
				{"employees","employees_leaves","Employee Leaves","hrm.module.EmployeeLeavesApplicationModule"}
	
		};
		
		List<Menu> childMenus = new ArrayList<>();
		i = 0;
		for ( String[] item : childs ) {
			i++;
			Menu parentMenu = db.find(Menu.class, item[0]);
			Menu menu = new Menu();
			menu.setParent(parentMenu);
			menu.setId(item[1]);
			menu.setTitle(item[2]);
			menu.setModuleClassName(item[3]);
			menu.setOrderNo(i);
			menu.setIcon("fa fa-square-o");
			
			Role role = db.find(Role.class, "admin");
			menu.getRoles().add(role);
			
			childMenus.add(menu);
			
			System.out.println("Menu: " + menu.getParent().getTitle() + " - " + menu.getTitle() + ", " + menu.getModuleClassName());
		}
		
		db.save(childMenus.toArray());
		
		
	}

}
