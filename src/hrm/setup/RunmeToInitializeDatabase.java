package hrm.setup;

import lebah.db.entity.Persistence;

public class RunmeToInitializeDatabase {
	
	public static void main(String[] args) throws Exception {
		
		CreateHRMenus.run();
		
		Persistence.db().close();
		
	}

}
