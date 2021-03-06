package uk.co.luciditysoftware.actsintown.domain.parametersets.spot;

import java.util.Date;

import uk.co.luciditysoftware.actsintown.domain.entities.Town;
import uk.co.luciditysoftware.actsintown.domain.entities.User;
import uk.co.luciditysoftware.actsintown.domain.enums.BookedState;

public class AddParameterSet {
    private User user;
    private Date scheduledFor;
    private Integer durationMinutes;
    private Town town;
    private String venueName;
    private String description;
    private BookedState bookedState;
    
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
    
    public Integer getDurationMinutes() {
        return durationMinutes;
    }
    
    public void setDurationMinutes(Integer durationMinutes) {
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
