package uk.co.luciditysoftware.actsintown.api.requests.spot;

import java.util.Date;
import java.util.UUID;

public class AddRequest {
    private Date scheduledFor;
    private int durationMinutes;
    private UUID townId;
    private String venueName;
    
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
	
	public UUID getTownId() {
		return townId;
	}
	
	public void setTownId(UUID townId) {
		this.townId = townId;
	}
	public String getVenueName() {
		return venueName;
	}
	
	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}    
}
