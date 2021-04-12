package hrm.module;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import hrm.entity.EventCalendar;
import hrm.entity.State;
import lebah.db.entity.Persistence;
import lebah.module.LebahUserModule;
import lebah.portal.action.Command;

public class EventCalendarModule extends LebahUserModule {
	
	String path = "apps/eventCalendar";
	
	@Override
	public String start() {
		listEvents();
		return path + "/start.vm";
	}
	
	@Command("listEvents")
	public String listEvents() {
		
		List<EventCalendar> events = db.list("select c from EventCalendar c order by c.fromDate");
		context.put("events", events);
		
		return path + "/listEvents.vm";
	}
	
	@Command("addNewEvent")
	public String addNewEvent() {
		context.remove("event");
		SelectList.listStates(context);
		return path + "/event.vm";
	}

	@Command("saveNewEvent")
	public String saveNewEvent() {
		EventCalendar event = new EventCalendar();
		event.setCode(getParam("eventCode"));
		event.setName(getParam("eventName"));
		event.setDescription(getParam("eventDescription"));
		event.setFromDate(Util.toDate(getParam("fromDate")));	
		if ( "".equals(getParam("toDate")))
			event.setToDate(Util.toDate(getParam("fromDate")));
		else 
			event.setToDate(Util.toDate(getParam("toDate")));
		
		event.setHoliday("1".equals(getParam("eventIsHoliday")));
		
		String[] stateIds = request.getParameterValues("stateIds");
		if (stateIds != null ) {
			List<State> states = Stream.of(stateIds)
									.map(stateId -> db.find(State.class, stateId))
									.collect(Collectors.toList());
			
			states.stream()
				.filter(s ->!event.getStates().contains(s))
				.forEach(s -> event.getStates().add(s));;
		}
		
		db.save(event);
		return listEvents();
	}
	
	@Command("editEvent")
	public String editEvent() {
		EventCalendar event = db.find(EventCalendar.class, getParam("eventId"));
		context.put("event", event);
		
		SelectList.listStates(context);
		
		return path + "/event.vm";
	}
	
	@Command("updateEvent")
	public String updateEvent() {
		//should clear all states first
		EventCalendar event = db.find(EventCalendar.class, getParam("eventId"));
		
		event.getStates().clear();
		db.update(event);
		
		event.setCode(getParam("eventCode"));
		event.setName(getParam("eventName"));
		event.setDescription(getParam("eventDescription"));
		event.setFromDate(Util.toDate(getParam("fromDate")));		
		if ( "".equals(getParam("toDate")))
			event.setToDate(Util.toDate(getParam("fromDate")));
		else 
			event.setToDate(Util.toDate(getParam("toDate")));
		event.setHoliday("1".equals(getParam("eventIsHoliday")));
		
		String[] stateIds = request.getParameterValues("stateIds");
		if (stateIds != null ) {
			Stream.of(stateIds)
					.map(stateId -> db.find(State.class, stateId))
					.collect(Collectors.toList())
					.stream().filter(s ->!event.getStates().contains(s))
					.forEach(s -> event.getStates().add(s));
		}
		
		db.update(event);
		
		return listEvents();
	}
	
	@Command("deleteEvent")
	public String deleteEvent() {
		context.remove("delete_error");
		EventCalendar event = db.find(EventCalendar.class, getParam("eventId"));
		try {
			db.delete(event);
		} catch ( Exception e ) {
			context.put("delete_error", "Can't delete event: " + e.getMessage());
		}
		
		return listEvents();
	}
	
	
	
}
