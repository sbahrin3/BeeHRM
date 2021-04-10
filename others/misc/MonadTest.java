package misc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import hrm.entity.EventCalendar;
import hrm.entity.State;
import lebah.db.entity.Persistence;

public class MonadTest {
	
	public static void main(String[] args ) {
		
		String[] stateIds = {"03","04","05","06"};
		Persistence db = Persistence.db();
		EventCalendar event = db.find(EventCalendar.class, "ce08c8bf6721c939ad8bc74179993cea0fb355ca");
		
		//COMPARE A AND B... THEY BOTH SERVE THE SAME PURPOSE
		
		//A) CODES CARA LAMA
		List<State> states = new ArrayList<>();
		for ( String stateId : stateIds ) {
			State state = db.find(State.class, stateId);
			states.add(state);
		}
		for ( State state : states ) {
			if ( !event.getStates().contains(state)) {
				event.getStates().add(state);
			}
		}

		
		//B) CODES WITH MONADS
		Stream.of(stateIds)								// jadikan array stateIds sebagai monads
		.map(stateId -> db.find(State.class, stateId))  // untuk setiap stateid, tukarkan kepada objek State
		.collect(Collectors.toList())					// dan jadikan List
		.stream()										// tukar List kepada monads
		.filter(s ->!event.getStates().contains(s))		// jika objek event tidak ada objek state
		.forEach(s -> event.getStates().add(s));		// tambah objek state ini kepada objek event
		
		
		
	}

}
