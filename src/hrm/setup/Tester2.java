package hrm.setup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import hrm.entity.Timedemo;
import lebah.db.entity.Persistence;

public class Tester2 {
	
	public static void main(String[] args) throws Exception {
		t1();
		t2();
	}
	
	private static void t1() throws ParseException {
		Persistence.db().execute("delete from Timedemo t");
		String date_ = "15/05/2021";
		String timeIn_ = "09:15 AM";
		String timeOut_ = "06:35 PM";
		Date date = new SimpleDateFormat("dd/MM/yyyy").parse(date_);
		Date timeIn = new SimpleDateFormat("hh:mm a").parse(timeIn_);
		Date timeOut = new SimpleDateFormat("hh:mm a").parse(timeOut_);
		
		Timedemo t = new Timedemo();
		t.setDate(date);
		t.setTimeIn(timeIn);
		t.setTimeOut(timeOut);
		Persistence.db().save(t);
	}
	
	private static void t2() throws ParseException {
		List<Timedemo> ts = Persistence.db().list("select t from Timedemo t");
		Timedemo t = ts.get(0);
		System.out.println(new SimpleDateFormat("dd/MM/yyyy").format(t.getDate()));
		System.out.println(new SimpleDateFormat("hh:mm a").format(t.getTimeIn()));
		System.out.println(new SimpleDateFormat("hh:mm a").format(t.getTimeOut()));
	}



}
