package hrm.setup;

import lebah.db.entity.Persistence;

public class Tester {
	
	public static void main(String[] args) {
		
		// this will let hibernate creates all tables declared in hibernate.cfg.xml
		Persistence db = Persistence.db();
		
	}

}
