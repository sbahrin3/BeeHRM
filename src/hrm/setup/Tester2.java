package hrm.setup;

import java.util.List;

import hrm.entity.District;
import lebah.db.entity.Persistence;

public class Tester2 {
	
	public static void main(String[] args) {
		
		Persistence db = Persistence.db();
		
		List<District> districts = db.list("select d from District d");
		districts.stream().forEach(d -> {
			System.out.println(d.getName() + ", " + d.getState().getName() + ", " + d.getState().getCountry().getName());
		});
		
	}

}
