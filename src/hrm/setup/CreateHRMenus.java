package hrm.setup;


import java.util.ArrayList;
import java.util.List;

import lebah.db.entity.Menu;
import lebah.db.entity.Persistence;
import lebah.db.entity.Role;
import lebah.db.entity.User;

public class CreateHRMenus {
	
	public static void main(String[] args) {
		checkRequiredRolesAndAdmin();
		run();
	}
	
	public static void checkRequiredRolesAndAdmin() {
		Persistence db = Persistence.db();
		
		String[][] roles = { {"admin", "Admin"}, {"anon", "Anon"}, {"user", "User"} };
		for ( String[] r : roles ) {
			
			Role role = db.find(Role.class, r[0]);
			if ( role == null ) {
				role = new Role();
				role.setId(r[0]);
				role.setName(r[1]);
				
				Persistence.db().save(role);
			
			}
		}
		
		
		
		User user = db.get("select u from User u where u.userName = 'admin'");
		
		if ( user == null ) {
			
			user = new User();
			
			user.setUserName("admin");
			user.setUserPassword("admin");
			
			user.setFirstName("Admin");
			
			Role role = Persistence.db().find(Role.class, "admin");
			user.setRole(role);
			
			Persistence.db().save(user);
		
		}
		
	}
	
	public static void run() {
		
		Persistence db = Persistence.db();
				
		db.execute("delete from Menu m where m.parent is not null");
		db.execute("delete from Menu m");
		
		String[][] parents = {
				{"company","Company"},
				{"administration","Administration"},
				{"employees","Employees"},
				{"employee_services","Employee Services"}
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
		
		
		String[][] childs = {
				{"company", "holding_company","Holding Company", "hrm.module.CompanyHQModule", "admin"},
				{"company","company_subsidiaries","Subsidiaries Company","hrm.module.CompanySubsidiariesModule", "admin"},
				
				{"administration","job_levels","Job Levels","hrm.module.SetupJobLevelModule", "admin"},
				{"administration","job_positions","Job Titles","hrm.module.SetupJobModule", "admin"},
				{"administration","salary_allowances","Salary Allowances","hrm.module.SetupSalaryAllowancesModule", "admin"},
				{"administration","salary_deductions","Salary Deductions","hrm.module.SetupSalaryDeductionsModule", "admin"},
				{"administration","salary_entitlements","Salary Entitlements","hrm.module.SetupSalaryConfigModule", "admin"},
				{"administration","leave_types","Leave Types","hrm.module.SetupLeaveModule", "admin"},
				{"administration","leave_entitlements","Leave Entitlements","hrm.module.SetupLeaveEntitlementModule", "admin"},
				{"administration","events_calendar","Holidays Calendar","hrm.module.EventCalendarModule", "admin"},
				{"administration","weekends_states","Weekends by States","hrm.module.SetupWeekendTypeByStatesModule", "admin"},
				{"administration","setup_projects","Projects","hrm.module.SetupProjectsModule", "admin"},
				
				{"employees","employees_records","Employees Records","hrm.module.ManageEmployeesModule", "admin"},
				{"employees","employees_leaves","Employee Leaves","hrm.module.EmployeeLeaveApplicationModule", "admin"},
				{"employees","employees_timesheets","Employee Timesheet","hrm.module.EmployeeTimesheetModule", "admin"},
				
				{"employee_services","my_calendar","Calendar","hrm.module.MyCalendarModule", "user"},
				{"employee_services","my_timesheets","Timesheet","hrm.module.MyTimesheetModule", "user"},
				{"employee_services","my_leaves","Leave","hrm.module.MyLeaveApplicationModule", "user"}
	
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
			
			Role role = db.find(Role.class, item[4]);
			menu.getRoles().add(role);
			
			childMenus.add(menu);
			
			System.out.println("Menu: " + menu.getParent().getTitle() + " - " + menu.getTitle() + ", " + menu.getModuleClassName());
		}
		
		db.save(childMenus.toArray());
		
		
	}

}
