package lebah.module;

import lebah.db.entity.Role;
import lebah.db.entity.User;
import lebah.portal.action.Command;
import lebah.portal.action.LebahModule;

/**
 * 
 * @author Shamsul Bahrin
 *
 */

public class SignUpModule extends LebahModule {
	
	String path = "apps/signUp";

	@Override
	public String start() {
		return path + "/start.vm";
	}
	
	@Command("signUp")
	public String signUp() {
		
		context.put("hasError", false);
		context.put("errorCode", "");
		
		String name = getParam("reg_name");
		String username = getParam("reg_username");
		String password = getParam("reg_password");
		String password2 = getParam("reg_password2");
		String telephone = getParam("reg_telephone");
		String email = getParam("reg_email");
		
		
		if ( password.equals(password2) ) {
			
			if ( db.list("select u from User u where u.userName = '" + username + "'").size() == 0 ) {
				
				Role role = db.find(Role.class, "user");
			
				User user = new User();
				user.setRole(role);
				user.setFirstName(name);
				user.setUserName(username);
				user.setUserPassword(password);
				user.setTelephone(telephone);
				user.setEmail(email);
								
				db.save(user);
			
			} else {
				context.put("hasError", true);
				context.put("errorCode", "user_exist");
			}
			
		} else {
			context.put("hasError", true);
			context.put("errorCode", "password_error");
		}		
		
		
		
		return path + "/signUp.vm";
	}

}
