package lebah.module;

import lebah.db.entity.User;
import lebah.portal.action.LebahModule;

public class StressTesterModule extends LebahModule {
	
	
	private String path = "apps/test";

	@Override
	public String start() {
		
		User user = db.get("select u from User u where u.id = 'ali'");
		context.put("user", user);
		
		
		return path + "/start.vm";
	}

}
