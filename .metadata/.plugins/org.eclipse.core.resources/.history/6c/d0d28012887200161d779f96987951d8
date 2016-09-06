package uk.co.luciditysoftware.actsintown.api.requests.spot;

import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class AddRequest {
    @NotNull(message = "This field is required")
    private Date scheduledFor;
    
    @NotNull(message = "This field is required")
    private int durationMinutes;
    
    @NotNull(message = "This field is required")
    private UUID townId;
    
    @NotNull(message = "This field is required")
	@Length(max = 50)
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
