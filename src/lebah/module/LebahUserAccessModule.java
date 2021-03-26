package lebah.module;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import lebah.db.entity.Menu;
import lebah.db.entity.Role;
import lebah.db.entity.User;
import lebah.portal.action.LebahModule;

/**
 * 
 * @author Shamsul Bahrin
 *
 */

public class LebahUserAccessModule extends LebahModule {
	
	protected User loginUser = null;
	private Map<String, Menu> accessMap = null;
	
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
			
			loginUser = user;
			
			Role userRole = user.getRole();
			
			if ( userRole == null ) {
				try {
					response.sendRedirect("../expired.jsp?msg=2");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				String classname = this.className;
				Menu module = null;
				accessMap = (HashMap) request.getSession().getAttribute("__access_map");
				if ( accessMap == null ) {
					accessMap = new HashMap<>();
					request.getSession().setAttribute("__access_map", accessMap);
				}
				module = accessMap.get(classname);
				if ( module == null ) {
					module = (Menu) db.get("select m from Menu m Join m.roles r where r.id = '" + userRole.getId() + "' and m.moduleClassName = '" + classname + "' ");
					if ( module == null ) {
						
						System.out.println("CHECK ACCESS FOR SECONDARY ROLES...");
						
						List<Role> roles = user.getSecondaryRoles();						
						Hashtable h = new Hashtable();
						h.put("roles", roles);
						h.put("classname", classname);
						String q = "select m from Menu m Join m.roles r where r in :roles and m.moduleClassName = :classname ";
						List<Menu> menus = db.list(q, h);
						if ( menus.size() > 0 ) module = menus.get(0);
						if ( module != null ) 
							accessMap.put(classname, module);
					}
					else {
						accessMap.put(classname, module);
					}
				}
				else {
					//System.out.println("GET MODULE PRIVILLEGE FROM ACCESS MAPPER: " + classname);
				}
				if ( module == null ) {
					try {
						response.sendRedirect("../expired.jsp");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		context.put("command", command);
	}

	@Override
	public String start() {
		return null;
	}
	


}
