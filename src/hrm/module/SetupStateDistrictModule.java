package hrm.module;

import java.util.List;

import hrm.entity.Country;
import hrm.entity.District;
import hrm.entity.State;
import lebah.module.LebahUserModule;
import lebah.portal.action.Command;


/**
 * 
 * @author shamsulbahrin
 *
 */
public class SetupStateDistrictModule extends LebahUserModule {
	
	String path = "apps/setupStateDistrict";
	
	@Override
	public String start() {
		
		listCountries();
		
		return path + "/start.vm";
	}
	
	@Command("listCountries")
	public String listCountries() {
		
		List<Country> countries = db.list("select c from Country c");
		context.put("countries", countries);
		
		return path + "/listCountries.vm";
	}
	
	@Command("listStates")
	public String listStates() {
		
		String countryId = getParam("countryId");
		Country country = db.find(Country.class, countryId);
		context.put("country", country);
		
		List<State> states = db.list("select s from State s where s.country.id = '" + country.getId() + "' order by s.id");
		context.put("states", states);
		
		return path + "/listStates.vm";
	}
	
	
	@Command("listDistricts")
	public String listDistricts() {
		
		String stateId = getParam("stateId");
		State state = db.find(State.class, stateId);
		context.put("state", state);
		
		List<District> districts = db.list("select d from District d where d.state.id = '" + state.getId() + "' order by d.id");
		context.put("districts", districts);
		
		return path + "/listDistricts.vm";
	}
	
	

}
