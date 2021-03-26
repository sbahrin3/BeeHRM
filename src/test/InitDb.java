package test;

import lebah.db.entity.Persistence;
import lebah.db.entity.Role;
import lebah.db.entity.User;

public class InitDb {
	
	public static void main(String[] args) throws Exception {
		
		createRoles();
		createAdmin();
		
	}
	
	
	public static void createRoles() {
		
		String[][] roles = { {"admin", "Admin"}, {"anon", "Anon"}, {"user", "User"} };
		for ( String[] r : roles ) {
			System.out.println(r[0]);
			System.out.println(r[1]);
			System.out.println("---");
			
			Role role = new Role();
			role.setId(r[0]);
			role.setName(r[1]);
			
			Persistence.db().save(role);
		}
	}
	
	public static void createAdmin() {
		
		User user = new User();
		user.setUserName("admin");
		user.setUserPassword("admin");
		
		user.setFirstName("Admin");
		
		Role role = Persistence.db().find(Role.class, "admin");
		if ( role != null) user.setRole(role);
		
		Persistence.db().save(user);
		
	}

}
