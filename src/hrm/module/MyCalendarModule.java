package hrm.module;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import hrm.entity.EventCalendar;
import lebah.db.entity.Persistence;
import lebah.module.LebahUserModule;
import lebah.portal.action.Command;

public class MyCalendarModule extends LebahUserModule {
	
	String path = "apps/myCalendar";
	int currentYear, currentMonth, currentDay;
	
	@Override
	public String start() {
		getCalendar();
		return path + "/start.vm";
	}
	
	@Command("getCalendar")
	public String getCalendar() {
		Date today = new Date();
		MyCalendar myc = new MyCalendar(today);
		context.put("c", myc);
		
		Calendar c1 = Calendar.getInstance();
		c1.set(myc.getYear(), myc.getMonth(), 1);
		c1.add(Calendar.DATE, -1);
		
		return path + "/calendar.vm";
	}
	

	public static void main(String[] args) {
				
		Date today = new Date();
		MyCalendar myc = new MyCalendar(today);
		
		IntStream.range(1, myc.getTotalDays()+1).forEach(i -> {
			List<EventCalendar> evs = myc.getEvents(i);
			System.out.print(i + "" );
			if ( evs.size() > 0 ) {
				evs.forEach(e -> {
					System.out.print(e.getName() + ", ");
				});
			}
			System.out.println();
		});
		
			
		
		
		
	}


}
