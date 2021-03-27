package hrm.module;

import lebah.portal.action.LebahModule;

public class AboutModule extends LebahModule {
	
	String path = "apps/about";
	
	@Override
	public String start() {
		
		return path + "/start.vm";
	}
	

}
