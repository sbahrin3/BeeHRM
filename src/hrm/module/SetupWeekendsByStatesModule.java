package hrm.module;

import lebah.module.LebahUserModule;
import lebah.portal.action.Command;

public class SetupWeekendsByStatesModule extends LebahUserModule {
	
	String path = "apps/setupWeekendsByStates";
	
	@Override
	public String start() {
		listStatesWeekends();
		return path + "/start.vm";
	}
	
	@Command("listStatesWeekends")
	public String listStatesWeekends() {
		
		return path + "/statesWeekends.vm";
	}

}
