package uk.co.luciditysoftware.actsintown.api.requests.spot;

import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import uk.co.luciditysoftware.actsintown.domain.enums.BookedState;

public class AddRequest {
    @NotNull(message = "This field is required")
    private Date scheduledFor;
    
    private Integer durationMinutes;
    
    @NotNull(message = "This field is required")
    private UUID townId;
    
    @Length(max = 50)
    private String venueName;

    @Length(max = 250)
    private String description;
    
    @NotNull(message = "This field is required")
    private BookedState bookedState;
    
    public Date getScheduledFor() {
        return scheduledFor;
    }
    
    public void setScheduledFor(Date scheduledFor) {
        this.scheduledFor = scheduledFor;
    }
    
    public Integer getDurationMinutes() {
        return durationMinutes;
    }
    
    public void setDurationMinutes(Integer durationMinutes) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BookedState getBookedState() {
        return bookedState;
    }

    public void setBookedState(BookedState bookedState) {
        this.bookedState = bookedState;
    }    
}
