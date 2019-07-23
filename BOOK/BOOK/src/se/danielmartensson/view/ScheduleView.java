package se.danielmartensson.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
 
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import se.danielmartensson.database.Jdbc;
import se.danielmartensson.login.Login;
 
@ManagedBean
@SessionScoped
public class ScheduleView implements Serializable {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ScheduleModel eventModel;
     
    //private ScheduleModel lazyEventModel;
 
    private ScheduleEvent event = new DefaultScheduleEvent();
    
    private Login login;
    
    private CopyEvent copyEvent;

	private ScheduleEvent eventCopy;

    public ScheduleView(Login login) {
        eventModel = new DefaultScheduleModel();
        copyEvent = new CopyEvent();
        this.login = login;
    }
    
    /*
     * Clear all events on the UI
     */
    
    public void clearEvents() {
    	eventModel.clear();
    }
    
    /*
     * Load events from the database at start
     */
    public void loadEvents() {
    	// Clear events
    	clearEvents();
    	
    	// Get Jdbc
    	Jdbc jdbc = login.getJdbc();
    	
    	// Get all events!
    	ArrayList<ScheduleEvent> rowList = jdbc.getRowList();
    	for(ScheduleEvent event : rowList) {
    		eventModel.addEvent(event);
    	}
    	
    }
     
    public Date getRandomDate(Date base) {
        Calendar date = Calendar.getInstance();
        date.setTime(base);
        date.add(Calendar.DATE, ((int) (Math.random()*30)) + 1);    //set random day of month
         
        return date.getTime();
    }
     
    public Date getInitialDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), Calendar.FEBRUARY, calendar.get(Calendar.DATE), 0, 0, 0);
         
        return calendar.getTime();
    }
     
    public ScheduleModel getEventModel() {
        return eventModel;
    }
     
    public ScheduleEvent getEvent() {
        return event;
    }
 
    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }
    
    public boolean isCollide() {
        
        // Current event 
    	Date startDate = event.getStartDate();
    	Date endDate = event.getEndDate();
    	
    	// We begin with assuming that all meetings will not collide
    	// Then it up to the for-loop to prove that the meeting will collide
    	boolean collide = false;
        
        // Loop though all events and check if it will collide with some meeting
        List<ScheduleEvent> listEvents = eventModel.getEvents();
        for(ScheduleEvent event_db : listEvents) {
        	// From database
        	Date startDate_db = event_db.getStartDate();
        	Date endDate_db = event_db.getEndDate();
        	
        	
        	// X.compareTo(Y) = -1 -> X is before Y
        	
        	int A = startDate.compareTo(startDate_db);
        	int B = endDate.compareTo(startDate_db);
        	int C = startDate.compareTo(endDate_db);
        	int D = endDate.compareTo(endDate_db);
        	
        	// Find where the meetings collide
        	System.out.println("A = " + A + " B = " + B + " C = " + C + " D = " + D);
        	
        	if(A == 1 && B == 1 && C == -1 && D == 1) {
        		collide = true;
        	}else if(A == 1 && B == 1 && C == -1 && D == -1) {
        		collide = true;
        	}else if(A == -1 && B == 1 && C == -1 && D == -1) {
        		collide = true;
        	}else if(A == -1 && B == 1 && C == -1 && D == 1) {
        		collide = true;
        	}else if(A == 0 && B == 1 && C == -1 && D == 0) {
        		collide = true;
        	}else if(A == 1 && B == 1 && C == -1 && D == 0) {
        		collide = true;
        	}else if(A == 0 && B == 1 && C == -1 && D == -1) {
        		collide = true;
        	}
        	
        	
        }
        
        return collide;
    }
     
    public void addEvent(ActionEvent actionEvent) {
    	System.out.println("Event add");

    	// New event will be added, but check if it will collide with another meeting
    	boolean collide = isCollide();
    	
    	// Make sure that the start date need to be before the end date
    	Date startDate = event.getStartDate();
    	Date endDate = event.getEndDate();
    	
    	if((!collide || eventModel.getEvents().size() <= 0)&& startDate.compareTo(endDate) == -1) {
    		if(event.getId() == null) {
                eventModel.addEvent(event);
            }
            else {
                eventModel.updateEvent(event);
            }
    		
    		System.out.println("addEvent");
    		
    		// Get jdbc
            Jdbc jdbc = login.getJdbc();
            
            // Add to the database 
            jdbc.addEvent(event);
            System.out.println("addEvent");
            
            // Delete this event - we got this event when we pressed on the event
            if(eventCopy != null) {
            	jdbc.deleteEvent(eventCopy);
            	System.out.println("not null copy");
            }
             
            // Create an new event ready to use
            event = new DefaultScheduleEvent();
    	}

    }
 
    
    public void deleteEvent(ActionEvent actionEvent) {
    	eventModel.deleteEvent(event);
    	
    	// Get Jdbc
    	Jdbc jdbc = login.getJdbc();
    	
    	// Delete from database
    	jdbc.deleteEvent(event);
    }
     
    public void onEventSelect(SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();
        
        // We borrow this method for copy an object
        copyEvent.create(event, 0, 0, true);
        eventCopy = copyEvent.getPastEvent();
    }
     
    public void onDateSelect(SelectEvent selectEvent) {
        event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
        eventCopy = null; // Don't delete the last pressed event
    }
     
    public void onEventMove(ScheduleEntryMoveEvent eventMove) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Händelse flyttad", "Dagskillnad:" + eventMove.getDayDelta() + ", Minutskillnad:" + eventMove.getMinuteDelta());
        addMessage(message);
        
        // Get Jdbc
    	Jdbc jdbc = login.getJdbc();
    	
    	// Changes
    	int dayDelta = eventMove.getDayDelta();
    	int minuteDelta = eventMove.getMinuteDelta();
    	
    	// Copy event
    	boolean moveOnly = true;
    	copyEvent.create(eventMove.getScheduleEvent(), dayDelta, minuteDelta, moveOnly);
    	ScheduleEvent deleteEvent = copyEvent.getPastEvent();
    	
    	// New event will be added, but check if it will collide with another meeting
    	event = eventMove.getScheduleEvent();
    	eventModel.deleteEvent(eventMove.getScheduleEvent());
    	boolean collide = isCollide();
    	
    	if(!collide) {
    		// Update at database
        	jdbc.updateMove(deleteEvent, eventMove);
    	}
    	
    	// Load events
    	loadEvents();
    }
     
    public void onEventResize(ScheduleEntryResizeEvent eventResize) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Händelse ändrad", "Dagskillnad:" + eventResize.getDayDelta() + ", Minutskillnad:" + eventResize.getMinuteDelta());
        addMessage(message);
        
        // Get Jdbc
    	Jdbc jdbc = login.getJdbc();
    	
    	// Changes
    	int dayDelta = eventResize.getDayDelta();
    	int minuteDelta = eventResize.getMinuteDelta();
    	
    	// Copy event
    	boolean moveOnly = false;
    	copyEvent.create(eventResize.getScheduleEvent(), dayDelta, minuteDelta, moveOnly);
    	ScheduleEvent deleteEvent = copyEvent.getPastEvent();
    	
    	// New event will be added, but check if it will collide with another meeting
    	event = eventResize.getScheduleEvent();
    	eventModel.deleteEvent(eventResize.getScheduleEvent());
    	boolean collide = isCollide();
    	
    	if(!collide) {
    		// Update at database
        	jdbc.updateResize(deleteEvent, eventResize);
    	}
    	
    	// Load events
    	loadEvents();
    }
     
    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}