package hrm.module;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import hrm.entity.State;
import lebah.module.LebahUserModule;
import lebah.portal.action.Command;

public class SetupWeekendTypeByStatesModule extends LebahUserModule {
	
	String path = "apps/setupWeekendTypeByStates";
	
	@Override
	public String start() {
		listStates();
		return path + "/start.vm";
	}
	
	@Command("listStates")
	public String listStates() {
		
		List<State> states = db.list("select s from State s order by s.id");
		context.put("states", states);
		
		return path + "/listStates.vm";
	}
	
	@Command("editStates")
	public String editStates() {
		return path + "/editStates.vm";
	}
	
	@Command("updateStates")
	public String updateState() {
		
		String[] stateIds = request.getParameterValues("stateIds");
		if ( stateIds != null ) {
			List<State> states = new ArrayList<>();
			Stream.of(stateIds).forEach(stateId -> {
				State state = db.find(State.class, stateId);
				state.setWeekendType(Util.getInt(getParam("weekendType_" + stateId)));
				states.add(state);
			});
			
			if ( states.size() > 0 ) db.update(states.toArray());
		}
		
		return listStates();
	}
	

}
