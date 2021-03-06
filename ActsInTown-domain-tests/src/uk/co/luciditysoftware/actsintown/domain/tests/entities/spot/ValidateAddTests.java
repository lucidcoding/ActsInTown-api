package uk.co.luciditysoftware.actsintown.domain.tests.entities.spot;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.co.luciditysoftware.actsintown.domain.common.ValidationMessage;
import uk.co.luciditysoftware.actsintown.domain.entities.Spot;
import uk.co.luciditysoftware.actsintown.domain.entities.Town;
import uk.co.luciditysoftware.actsintown.domain.entities.User;
import uk.co.luciditysoftware.actsintown.domain.parametersets.spot.AddParameterSet;

/**
 * Test cases for the ValidateAdd method of the Spot entity.
 * 
 * @author Paul Davies
 */
public class ValidateAddTests {
    private SimpleDateFormat dateFormat;
    private User user;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * Sets up a User entity with a related Spot entity for running tests against.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        dateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

        user = new User() {
            {
                setUsername("gary.green@green.com");
                setSpots(new ArrayList<Spot>());
            }
        };

        Spot existingSpot = new Spot() {
            {
                setUser(user);
                setScheduledFor(dateFormat.parse("16-11-2020 19:30:00"));
                setDurationMinutes(30);
                setTown(new Town() {
                    {
                        this.setName("Blueville");
                    }
                });
                setVenueName("Test Venue 01");
                setAddedOn(new Date());
                setCancelled(false);
            }
        };
        
        user.getSpots().add(existingSpot);
    }
    
    /**
     * Tests that when a parameter set for adding a Spot is validated against a User, if the requested Spot conflicts
     * with existing Spots, a validation error is returned.
     * 
     * @throws ParseException
     */
    @Test
    public void conflictingSpotReturnsValidationError() throws ParseException {
        AddParameterSet parameterSet = new AddParameterSet() {
            {
                this.setUser(user);
                this.setScheduledFor(dateFormat.parse("16-11-2020 19:45:00"));
                this.setDurationMinutes(30);
                this.setTown(new Town() {
                    {
                        this.setName("Greenville");
                    }
                });
                this.setVenueName("Test Venue 02");
            }
        };

        List<ValidationMessage> validationMessages = Spot.validateAdd(parameterSet);

        assertEquals(1, validationMessages.size());
    }
}
