package lebah.module;

import lebah.portal.action.LebahModule;

public class LandingPageModule extends LebahModule {
	
	String path = "/landingPage";
	
	

	@Override
	public String start() {
		
		return path + "/start.vm";
	}

}
