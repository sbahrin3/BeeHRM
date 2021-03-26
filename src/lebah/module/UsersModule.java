package lebah.module;

import java.util.List;

import javax.persistence.PersistenceException;

import lebah.db.entity.Persistence;
import lebah.db.entity.Role;
import lebah.db.entity.User;
import lebah.portal.action.Command;
import lebah.util.FileUploadUtil;

public class UsersModule extends LebahAdminModule {
	
	private String path = "vtl/modules/users";

	@Override
	public void preProcess() {
		super.preProcess();
		
		List<Role> roles = db.list("select r from Role r order by r.name");
		context.put("roles", roles);
		
	}

	@Override
	public String start() {
		try {
			listUsers();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path + "/start.vm";
	}
	
	@Command("listUsers")
	public String listUsers() throws Exception {
		List<User> users = db.list("select u from User u order by u.userName");
		context.put("users", users);
		return path + "/listUsers.vm";
	}
	
	@Command("addUser")
	public String addUser() throws Exception {
		context.remove("userData");
		return path + "/addUser.vm";
	}
	
	@Command("editUser")
	public String editUser() throws Exception {
		String userId = getParam("userId");
		User user = db.find(User.class, userId);
		context.put("userData", user);
		
		List<Role> roles = user.getRole() != null ? 
				db.list("select r from Role r where r.id <> '" + user.getRole().getId() + "' order by r.name") :
					db.list("select r from Role r order by r.name");
		context.put("otherRoles", roles);
		
		return path + "/addUser.vm";
	}
	
	@Command("save")
	public String save() throws Exception {
		String userId = getParam("userId");
		boolean add = "".equals(userId);
		User user = add ? new User() : db.find(User.class, userId);
		boolean canUpdate = true;
		if ( add ) {
			String userName = getParam("userName");
			if ( db.find(User.class, userName) == null ) {
				user.setId(userName);
				user.setUserName(userName);
				user.setUserPassword("123");
			} else {
				canUpdate = false;
			}
		}
		
		if ( canUpdate ) {
			user.setFirstName(getParam("firstName"));
			user.setLastName(getParam("lastName"));
			user.setPosition(getParam("position"));
			user.setDepartment(getParam("department"));
			user.setTelephone(getParam("telephone"));
			user.setFax(getParam("fax"));
			user.setEmail(getParam("email"));
			user.setInitial(getParam("initial"));
			
			String roleId = getParam("roleId");
			Role role = db.find(Role.class, roleId);
			if ( role != null ) user.setRole(role);
			
			/*
			String otherRoleId = getParam("otherRoleId");
			if ( !"".equals(otherRoleId)) {
				Role otherRole = db.find(Role.class, otherRoleId);
				
				if ( !user.getSecondaryRoles().contains(otherRole)) {
					user.getSecondaryRoles().clear();
					user.getSecondaryRoles().add(otherRole);
				}
			} else {
				user.getSecondaryRoles().clear();
			}
			*/
			
			user.getSecondaryRoles().clear();
			String[] roleIds = request.getParameterValues("otherRoleIds");
			if ( roleIds != null ) {
				for ( String id : roleIds ) {
					Role otherRole = db.find(Role.class, id);
					if ( !user.getSecondaryRoles().contains(otherRole)) {
						user.getSecondaryRoles().add(otherRole);
					}
				}
			}
			
			db.ifAdd(add).saveOrUpdate(user);
		
		}
		context.put("userData", user);
		context.put("canUpdate", canUpdate);
		
		List<Role> roles = db.list("select r from Role r where r.id <> '" + user.getRole().getId() + "' order by r.name");
		context.put("otherRoles", roles);
		
		return path + "/addUser.vm";
		
	}
	
	@Command("deleteUser")
	public String deleteUser() throws Exception {
		context.remove("error");
		String userId = getParam("userId");
		User user = db.find(User.class, userId);
		//delete user only if it contains no related data
		boolean canDelete = true;
		try {
			if ( canDelete ) db.delete(user);
			
		} catch ( Exception e ) { //javax.persistence.PersistenceException, org.hibernate.exception.ConstraintViolationException
			if ( e instanceof PersistenceException ) {
				System.out.println("Can't delete data because of CONSTRAINT VIOLATION.");
				context.put("error", "error_constraint_violation");
			}
		}
		context.put("canDelete", canDelete);
		return listUsers();
	}
	
	@Command("uploadFile")
	public String uploadFile() throws Exception {
		String divUploadStatusName = getParam("divUploadStatusName");
		context.put("divUploadStatusName", divUploadStatusName);
		String elementName = getParam("elementName");
		
		String uploadDir = AppProperties.uploadDir();
		
		String savedFileName = FileUploadUtil.upload(request, elementName, uploadDir);
		context.put("savedFileName", savedFileName);
		
		String userId = getParam("userId");
		User user = db.find(User.class, userId);
		
		String imageType = getParam("imageType");
		
		System.out.println("image type = " + imageType);
		
		if ( "profile".equals(imageType)) {
			user.setAvatarImageFileName(savedFileName);
		}
		else if ( "signature".equals(imageType)) {
			user.setSignatureImageFileName(savedFileName);
		}

		db.update(user);
		
		return path + "/uploaded.vm";
	}
	
	
	public static void main(String[] args) throws Exception {
		Persistence db = Persistence.db();
		
		User user = db.find(User.class, "ali");
		List<Role> roles = user.getSecondaryRoles();
		roles.forEach(r -> {
			System.out.println(r.getId());
		});
		
		Role role = db.find(Role.class, "user");
		System.out.println(user.getSecondaryRoles().contains(role));
		
		db.close();
	}

}
