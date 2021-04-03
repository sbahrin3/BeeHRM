package hrm.setup;

import java.text.SimpleDateFormat;
import java.util.List;

import hrm.entity.Timedemo;
import lebah.db.entity.Persistence;

public class Tester {
	
	public static void main(String[] args) {
		
		// this will let hibernate creates all tables declared in hibernate.cfg.xml
		Persistence db = Persistence.db();
		
		List<Timedemo> ts = Persistence.db().list("select t from Timedemo t");
		Timedemo t = ts.get(0);
		System.out.println(new SimpleDateFormat("dd/MM/yyyy").format(t.getDate()));
		System.out.println(new SimpleDateFormat("hh:mm a").format(t.getTimeIn()));
		System.out.println(new SimpleDateFormat("hh:mm a").format(t.getTimeOut()));
		
	}

}
