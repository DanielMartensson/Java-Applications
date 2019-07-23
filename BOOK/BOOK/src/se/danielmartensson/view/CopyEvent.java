package se.danielmartensson.view;

import java.util.Date;

import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.ScheduleEvent;

public class CopyEvent {
	
	private ScheduleEvent updatedEvent;
	private ScheduleEvent pastEvent;
	private int dayDelta;
	private int minuteDelta;
	private final int oneMinuteUnix = 60; // One minute in unix time
	private final int oneDayUnix = 86400; // One day in unix time 
	
	public void create(ScheduleEvent updatedEvent, int dayDelta, int minuteDelta, boolean moveOnly) {
		this.updatedEvent = updatedEvent;
		this.dayDelta = dayDelta;
		this.minuteDelta = minuteDelta;
		
		// Start the procedure to find the past event from the updated one
		modify(moveOnly);
	}

	
	private void modify(boolean moveOnly) {
		
		// Get the unix times
		long startUnixTime = 0;
		long endUnixTime = 0;
		String title = "";
		
		// Use try-catch if the updateEvent is null
		try {
			startUnixTime = updatedEvent.getStartDate().getTime() / 1000L;
			endUnixTime = updatedEvent.getEndDate().getTime() / 1000L;
			
			// Find title
			title = updatedEvent.getTitle();
			
		}catch(NullPointerException e) {
			
		}
		
		
		// Compute the past unix time
		if(moveOnly) {
			startUnixTime = startUnixTime - oneDayUnix*dayDelta - oneMinuteUnix*minuteDelta;
			endUnixTime = endUnixTime - oneDayUnix*dayDelta - oneMinuteUnix*minuteDelta;
		}else {
			// If we only resize, which means modify the end time
			endUnixTime = endUnixTime - oneDayUnix*dayDelta - oneMinuteUnix*minuteDelta;
		}
		
		// Create the past dates
		Date startDatePast = new Date(startUnixTime*1000L);
		Date endDateDatePast = new Date(endUnixTime*1000L);
		
		
		
		// Compute the past event
		pastEvent = new DefaultScheduleEvent(title, startDatePast, endDateDatePast);
	}
	
	public ScheduleEvent getPastEvent() {
		return pastEvent;
	}
    
    
}
