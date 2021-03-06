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
import uk.co.luciditysoftware.actsintown.domain.enums.BookedState;
import uk.co.luciditysoftware.actsintown.domain.parametersets.spot.AddParameterSet;

/**
 * Entity class representing a spot in the Acts In Town system. This can be a spot that has been registered by an act that is 
 * performing, or an available spot registered by a venue/promoter  that requires filling.
 * @author Paul Davies
 */
public class Spot extends Entity  {
	
	/**
	 * The user who has registered this spot - this can be the act who is performing or the venue that has availablity.
	 */
    private User user;
    
    /**
     * The scheduled start time of the slot
     */
    private Date scheduledFor;
    
    /**
     * The scheduled duration for the spot in minutes
     */
    private Integer durationMinutes;
    
    /**
     * The town in which the spot is located
     */
    private Town town;
    
    /**
     * The name of the venue in which this spot is located
     */
    private String venueName;
    
    /**
     * The date and time on which this spot was entered into the system
     */
    private Date addedOn;
    
    /**
     * Whether this spot has been cancelled
     */
    private boolean cancelled;
    
    /**
     * The current state of the booking of this spot (booked/available)
     */
    private BookedState bookedState;
    
    /**
     * The description of the spot
     */
    private String description;
    
    /**
     * Gets the user who has registered this spot.
     * @return The user who has registered this spot
     */
	public User getUser() {
		return user;
	}
	
	/**
	 * Sets the user who has registered the spot.
	 * @param user The user who has registered this spot
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * Gets the scheduled start time of the slot.
	 * @return The scheduled start time of the slot
	 */
	public Date getScheduledFor() {
		return scheduledFor;
	}
	
	/**
	 * Sets the scheduled start time of the slot.
	 * @param scheduledFor The scheduled start time of the slot
	 */
	public void setScheduledFor(Date scheduledFor) {
		this.scheduledFor = scheduledFor;
	}
	
	/**
	 * Gets the scheduled duration for the spot in minutes.
	 * @return The scheduled duration for the spot in minutes
	 */
	public Integer getDurationMinutes() {
		return durationMinutes;
	}
	
	/**
	 * Sets the scheduled duration for the spot in minutes.
	 * @param durationMinutes The scheduled duration for the spot in minutes.
	 */
	public void setDurationMinutes(Integer durationMinutes) {
		this.durationMinutes = durationMinutes;
	}
	
	/**
	 * Gets the town in which the spot is located.
	 * @return The town in which the spot is located
	 */
	public Town getTown() {
		return town;
	}
	
	/**
	 * Sets the town in which the spot is located.
	 * @param town The town in which the spot is located
	 */
	public void setTown(Town town) {
		this.town = town;
	}
	
	/**
	 * Gets the name of the venue in which this spot is located.
	 * @return The name of the venue in which this spot is located
	 */
	public String getVenueName() {
		return venueName;
	}
	
	/**
	 * Sets the name of the venue in which this spot is located
	 * @param venueName The name of the venue in which this spot is located
	 */
	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}
	
	/**
	 * Gets the date and time on which this spot was entered into the system.
	 * @return The date and time on which this spot was entered into the system
	 */
	public Date getAddedOn() {
		return addedOn;
	}
	
	/**
	 * Sets the date and time on which this spot was entered into the system.
	 * @param addedOn The date and time on which this spot was entered into the system
	 */
	public void setAddedOn(Date addedOn) {
		this.addedOn = addedOn;
	}
	
	/**
	 * Gets whether this spot has been cancelled.
	 * @return True if this spot has been cancelled
	 */
	public boolean isCancelled() {
		return cancelled;
	}
	
	/**
	 * Sets whether this spot has been cancelled.
	 * @param cancelled True if this spot has been cancelled
	 */
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	/**
	 * Gets the current state of the booking of this spot.
	 * @return The current state of the booking of this spot
	 */
	public BookedState getBookedState() {
		return bookedState;
	}

	/**
	 * Sets the current state of the booking of this spot.
	 * @param bookedState The current state of the booking of this spot
	 */
	public void setBookedState(BookedState bookedState) {
		this.bookedState = bookedState;
	}

	/**
	 * Gets the description of the spot.
	 * @return The description of the spot
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the spot.
	 * @param description The description of the spot
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Returns the scheduled end time of the slot.
	 * @return The scheduled end time of the slot
	 */
	public Date getScheduledEnd() {
		GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(scheduledFor);
        cal.add(Calendar.MINUTE, (durationMinutes != null) ? durationMinutes : 0);        
        Date scheduledEnd = cal.getTime();
        return scheduledEnd;
	}

	/**
	 * Validates the supplied parameter set to check that a slot can be added with those values.
	 * @param parameterSet The parameter set containing the values for the slot
	 * @return List of validation messages containing errors - empty if there were no errors.
	 */
	public static List<ValidationMessage> validateAdd(AddParameterSet parameterSet) {
		List<ValidationMessage> validationMessages = new ArrayList<ValidationMessage>();
		
		Date now = new Date();

		if (parameterSet.getScheduledFor() == null) {
			validationMessages.add(new ValidationMessage(
				ValidationMessageType.ERROR,
				"scheduledFor",
				"Scheduled for is required."));
		} else if (parameterSet.getScheduledFor().before(now)) {
			validationMessages.add(new ValidationMessage(
				ValidationMessageType.ERROR,
				"scheduledFor",
				"Scheduled for must not be in the past."));
		}

		if (parameterSet.getDurationMinutes() != null && parameterSet.getDurationMinutes() <= 0) {
			validationMessages.add(new ValidationMessage(
				ValidationMessageType.ERROR,
				"DurationMinutes",
				"Duration must be greater than zero."));
		}
	
		if (parameterSet.getUser() != null && parameterSet.getScheduledFor() != null && parameterSet.getDurationMinutes() != null
				&& parameterSet.getUser().getConflictingSpots(parameterSet.getScheduledFor(), parameterSet.getDurationMinutes()).size() > 0) {
			validationMessages.add(new ValidationMessage(
				ValidationMessageType.ERROR,
				"",
				"Spot conflicts with existing spots."));
		}
		
		return validationMessages;
	}
	
	/**
	 * Creates a new slot entity based on the values in the supplied parameter set.
	 * @param parameterSet The parameter set containing the values for the slot
	 * @return The newly created slot entity.
	 */
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
		spot.bookedState = parameterSet.getBookedState();
		spot.description = parameterSet.getDescription();
		return spot;
	}
}
