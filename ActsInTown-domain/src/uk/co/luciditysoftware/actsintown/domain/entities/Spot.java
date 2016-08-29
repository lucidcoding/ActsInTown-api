package uk.co.luciditysoftware.actsintown.domain.entities;

import java.util.Date;
import java.util.UUID;

import uk.co.luciditysoftware.actsintown.domain.common.Entity;
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
