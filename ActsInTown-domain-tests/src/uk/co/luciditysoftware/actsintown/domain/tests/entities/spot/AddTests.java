package uk.co.luciditysoftware.actsintown.domain.tests.entities.spot;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;

import uk.co.luciditysoftware.actsintown.domain.entities.Spot;
import uk.co.luciditysoftware.actsintown.domain.entities.Town;
import uk.co.luciditysoftware.actsintown.domain.entities.User;
import uk.co.luciditysoftware.actsintown.domain.parametersets.spot.AddParameterSet;
/**
 * Test cases for the Add method of the Spot entity.
 * 
 * @author Paul Davies
 */
public class AddTests {
    private SimpleDateFormat dateFormat;
    
    /**
     * Confirms that when a valid parameter set is passed to the Add method, a correct Spot entity is returned.
     * 
     * @throws ParseException
     */
    @Test
    public void spotIsCreatedCorrectly() throws ParseException
    {
        dateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        
        AddParameterSet parameterSet = new AddParameterSet()
            {{
                this.setUser(new User()
                    {{
                        this.setUsername("gary.green@green.com");
                    }});
                this.setScheduledFor(dateFormat.parse("16-11-2015 00:00:00"));
                this.setDurationMinutes(10);
                this.setTown(new Town()
                    {{
                        this.setName("Greenville");
                    }});
                this.setVenueName("The Comedy Club");
            }};
            
        Spot spot = Spot.add(parameterSet);
        assertEquals("gary.green@green.com", spot.getUser().getUsername());
        assertEquals(dateFormat.parse("16-11-2015 00:00:00"), spot.getScheduledFor());
        assertEquals((Integer)10, spot.getDurationMinutes());
        assertEquals("Greenville", spot.getTown().getName());
        assertEquals("The Comedy Club", spot.getVenueName());
        assertNotNull(spot.getAddedOn());
        assertFalse(spot.isCancelled());
    }
}
