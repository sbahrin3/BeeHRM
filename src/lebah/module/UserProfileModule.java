package lebah.module;

import java.util.List;

import lebah.db.entity.Role;
import lebah.db.entity.User;
import lebah.portal.action.Command;
import lebah.util.FileUploadUtil;
import lebah.util.PasswordService;

public class UserProfileModule extends LebahUserModule {
	
	private String path = "vtl/modules/userProfile";
	
	
	@Override
	public void preProcess() {
		super.preProcess();
		List<Role> roles = db.list("select r from Role r order by r.name");
		context.put("roles", roles);
	}
	


	@Override
	public String start() {
		String _portal_userid = (String) request.getSession().getAttribute("_portal_userid");
		context.put("_portal_user_id", "_portal_userid");
		User user = db.find(User.class, _portal_userid);
		context.put("userData", user);
		return path + "/start.vm";
	}
	
	@Command("save")
	public String save() throws Exception {
		String userId = (String) request.getSession().getAttribute("_portal_userid");
		if ( userId != null ) {
			User user = db.find(User.class, userId);
			boolean canUpdate = true;
			if ( canUpdate ) {
				user.setFirstName(getParam("firstName"));
				user.setLastName(getParam("lastName"));
				user.setPosition(getParam("position"));
				user.setDepartment(getParam("department"));
				user.setTelephone(getParam("telephone"));
				user.setFax(getParam("fax"));
				user.setEmail(getParam("email"));
				user.setInitial(getParam("initial"));
				db.update(user);
			
			}
			context.put("userData", user);
			context.put("canUpdate", canUpdate);
		
		} else {
			
		}
		
		return path + "/profile.vm";
	}
	
	
	@Command("uploadFile")
	public String uploadFile() throws Exception {
		String userId = (String) request.getSession().getAttribute("_portal_userid");
		if ( userId != null ) {
			System.out.println("uploadFile");
			String divUploadStatusName = getParam("divUploadStatusName");
			context.put("divUploadStatusName", divUploadStatusName);
			String elementName = getParam("elementName");
			String uploadDir = AppProperties.uploadDir();
			String savedFileName = FileUploadUtil.upload(request, elementName, uploadDir);
			context.put("savedFileName", savedFileName);
			
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
		
		} else {
			
		}
		
		return path + "/uploaded.vm";
	}
	
	@Command("savePassword")
	public String savePassword() throws Exception {
		
		context.remove("error");
		String oldPassword = getParam("old_password");
		String newPassword = getParam("new_password");
		String newPasswordConfirm = getParam("new_password_confirm");
		
		String userId = (String) request.getSession().getAttribute("_portal_userid");
		if ( userId != null ) {
			
			User user = db.find(User.class, userId);
			if (  user.getUserPassword().equals(PasswordService.encrypt(oldPassword))) {
				
				if ( !"".equals(newPassword) && !"".equals(newPasswordConfirm) && newPassword.equals(newPasswordConfirm)) {
					
					user.setUserPassword(newPassword);
					db.update(user);
					
				} else {
					//new password confirm doesnt match
					context.put("error", "password_confirm_not_match");
				}
				
				
			} else {
				//wrong old password
				context.put("error", "wrong_old_password");
			}
			
			
		}
		
		return path + "/password_saved.vm";
	}

}
