package hrm.module;

import java.util.List;

import org.apache.velocity.VelocityContext;

import hrm.entity.Address;
import hrm.entity.District;
import hrm.entity.State;
import lebah.db.entity.Persistence;

public class SelectList {
	
	
	public static void listStates(VelocityContext context, String countryId) {
		List<State> states = Persistence.db().list("select s from State s where s.country.id = '" + countryId + "' order by s.id");
		context.put("states", states);
		
	}
	
	public static void listDistricts(VelocityContext context, String stateId) {
		List<District> districts = Persistence.db().list("select d from District d where d.state.id = '" + stateId + "' order by d.id");
		context.put("districts", districts);
	}
	
	public static void listDistricts(VelocityContext context, Address address) {
		if ( address != null && address.getDistrict() != null && address.getDistrict().getState() != null) {
			List<District> districts = Persistence.db().list("select d from District d where d.state.id = '" + address.getDistrict().getState().getId() + "' order by d.id");
			context.put("districts", districts);
		} else {
			context.remove("districts");
		}
	}

}
