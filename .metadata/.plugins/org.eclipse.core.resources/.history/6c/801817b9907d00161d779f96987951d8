package uk.co.luciditysoftware.actsintown.domain.entities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import uk.co.luciditysoftware.actsintown.domain.common.Entity;
import uk.co.luciditysoftware.actsintown.domain.common.ValidationMessage;
import uk.co.luciditysoftware.actsintown.domain.common.ValidationMessageType;
import uk.co.luciditysoftware.actsintown.domain.parametersets.spot.AddParameterSet;

public class Spot extends Entity  {
    private User user;
    private Date scheduledFor;
    private int durationMinutes;
    private Town town;
    private String venueName;
    private Date addedOn;
    private boolean cancelled;
    
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Date getScheduledFor() {
		return scheduledFor;
	}
	
	public void setScheduledFor(Date scheduledFor) {
		this.scheduledFor = scheduledFor;
	}
	
	public int getDurationMinutes() {
		return durationMinutes;
	}
	
	public void setDurationMinutes(int durationMinutes) {
		this.durationMinutes = durationMinutes;
	}
	
	public Town getTown() {
		return town;
	}
	
	public void setTown(Town town) {
		this.town = town;
	}
	
	public String getVenueName() {
		return venueName;
	}
	
	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}
	
	public Date getAddedOn() {
		return addedOn;
	}
	
	public void setAddedOn(Date addedOn) {
		this.addedOn = addedOn;
	}
	
	public boolean isCancelled() {
		return cancelled;
	}
	
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	public Date getScheduledEnd() {
		GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(scheduledFor);
        cal.add(Calendar.MINUTE, durationMinutes);        
        Date scheduledEnd = cal.getTime();
        return scheduledEnd;
	}

	public static List<ValidationMessage> validateAdd(AddParameterSet parameterSet) {
		List<ValidationMessage> validationMessages = new ArrayList<ValidationMessage>();
		
		Date now = new Date();

		if (parameterSet.getScheduledFor() == null) {
			validationMessages.add(new ValidationMessage() {
				{
					setType(ValidationMessageType.ERROR);
					setField("scheduledFor");
					setText("Scheduled for is required.");
				}
			});
		} else if (parameterSet.getScheduledFor().before(now)) {
			validationMessages.add(new ValidationMessage() {
				{
					setType(ValidationMessageType.ERROR);
					setField("scheduledFor");
					setText("Scheduled for must not be in the past.");
				}
			});
		}

		//Ensure duration is psositve and > 0
	
		if (parameterSet.getUser() != null && parameterSet.getScheduledFor() != null 
				&& parameterSet.getUser().getConflictingSpots(parameterSet.getScheduledFor(), parameterSet.getDurationMinutes()).size() > 0) {
			validationMessages.add(new ValidationMessage() {
				{
					setType(ValidationMessageType.ERROR);
					setField("");
					setText("Spot conflicts with existing spots.");
				}
			});
		}
		
		return validationMessages;
	}
	
	public static Spot add(AddParameterSet parameterSet) {
		Spot spot = new Spot();
		spot.id = UUID.randomUUID();
		spot.user = parameterSet.getUser();
		spot.scheduledFor = parameterSet.getScheduledFor();
		spot.durationMinutes = parameterSet.getDurationMinutes();
		spot.town = parameterSet.getTown();
		spot.venueName = parameterSet.getVenueName();
		spot.addedOn = new Date();
		spot.cancelled = false;
		return spot;
	}
}
