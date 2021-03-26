package lebah.module;

import java.io.IOException;

import lebah.db.entity.Role;
import lebah.db.entity.User;
import lebah.portal.action.LebahModule;

/**
 * 
 * @author Shamsul Bahrin
 *
 */
public class LebahAdminModule extends LebahModule {
	

	
	@Override
	public void preProcess() {
		
		User user = (User) context.get("user");
		if ( user == null ) {
			try {
				response.sendRedirect("../expired.jsp?msg=1");
			} catch (IOException e) {
				e.printStackTrace();
			}
	
		} else {
			
			Role userRole = user.getRole();
			if ( userRole == null || !"admin".equals(userRole.getId())) {
				try {
					response.sendRedirect("../expired.jsp");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		System.out.println("command = " + command);
		context.put("command", command);
	}

	@Override
	public String start() {
		return null;
	}




}
