package uk.co.luciditysoftware.actsintown.api.datatransferobjects;

import java.util.Date;
import java.util.UUID;

public class SpotDto {
	public UUID id;
    //private User user;
	public Date scheduledFor;
	public int durationMinutes;
	public TownDto town;
	public String venueName;
	public Date addedOn;
	public boolean cancelled;
}