package misc;

import java.util.List;

import hrm.entity.Country;
import lebah.db.entity.Persistence;

public class ReadStates {
	
	
	public static void main(String[] args) {
		
		Persistence db = Persistence.db();
		
		System.out.println();
		System.out.println();
		
		List<Country> countries = db.list("select c from Country c");
		for ( Country d : countries ) {
			System.out.println("{\"" + d.getId() + "\", \"" + d.getName() + "\"}, ");
		}
		
		/*
		List<State> states = db.list("select s from State s");
		for ( State state : states ) {
			System.out.println("{\"" + state.getId() + "\", \"" + state.getName() + "\", \"" + state.getCountry().getId() + "\"}, ");
		}
		*/
		
		/*
		List<District> districts = db.list("select d from District d order by d.id");
		for ( District d : districts ) {
			System.out.println("{\"" + d.getId() + "\", \"" + d.getName() + "\", \"" + d.getState().getId() + "\"}, ");
		}
		*/
		
	}

}
