package uk.co.luciditysoftware.actsintown.domain.tests.entities.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.co.luciditysoftware.actsintown.domain.entities.Spot;
import uk.co.luciditysoftware.actsintown.domain.entities.Town;
import uk.co.luciditysoftware.actsintown.domain.entities.User;

/**
 * Test cases for the GetConflictingSpots method of the User entity.
 * 
 * @author Paul Davies
 */
public class GetConflictingSpotsTests {
    private User user;
    private SimpleDateFormat dateFormat;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * Creates a collection of spots for running the various tests against.
     * 
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        user = new User();
        List<Spot> spots = new ArrayList<Spot>();
        dateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

        spots.add(new Spot() {
            {
                setScheduledFor(dateFormat.parse("16-11-2020 19:30:00"));
                setDurationMinutes(30);
                setTown(new Town());
                setVenueName("Test Venue 02");
                setAddedOn(new Date());
                setCancelled(false);
            }
        });

        user.setSpots(spots);
    }

    /**
     * Test the case when start and end are before the earlier
     * booking. No conflicting bookings should be returned.
     * 
     * @throws ParseException
     */
    @Test
    public void nothingReturnedIfBeforeEarliestBooking() throws ParseException {
        // Act.
        List<Spot> result = user.getConflictingSpots(dateFormat.parse("16-11-2020 18:00:00"), 30);

        // Assert.
        assertEquals(0, result.size());
    }

    /**
     * Test the case when start date is before start of a conflicting booking,
     * and end date is equal to the start date. The conflicting booking should
     * be returned.
     * 
     * @throws ParseException
     */
    @Test
    public void returnsBookingIfStartDateBeforeAndEndDateEqualsStart() throws ParseException {
        // Act.
        List<Spot> result = user.getConflictingSpots(dateFormat.parse("16-11-2020 19:00:00"), 30);

        // Assert.
        assertEquals(1, result.size());
    }

    /**
     * Test the case when start date is before start of a conflicting booking,
     * and end date is after the start date. The conflicting booking should be
     * returned.
     * 
     * @throws ParseException
     */
    @Test
    public void returnsBookingIfStartDateIsBeforeAndEndIsAfterStart() throws ParseException {
        // Act.
        List<Spot> result = user.getConflictingSpots(dateFormat.parse("16-11-2020 19:15:00"), 30);

        // Assert.
        assertEquals(1, result.size());
    }

    /**
     * Test the case when start date is on the start date of a conflicting
     * booking, and end date is after the start date. The conflicting booking
     * should be returned.
     * 
     * @throws ParseException
     */
    @Test
    public void returnsBookingIfStartDateIsEqualAndEndIsAfterStart() throws ParseException {
        // Act.
        List<Spot> result = user.getConflictingSpots(dateFormat.parse("16-11-2020 19:30:00"), 15);

        // Assert.
        assertEquals(1, result.size());
    }

    /**
     * Test the case when start date is on the start date of a conflicting
     * booking, and end date is on the end date. The conflicting booking should
     * be returned.
     * 
     * @throws ParseException
     */
    @Test
    public void returnsBookingIfStartEqualsStartAndEndEqualsEnd() throws ParseException {
        // Act.
        List<Spot> result = user.getConflictingSpots(dateFormat.parse("16-11-2020 19:30:00"), 30);

        // Assert.
        assertEquals(1, result.size());
    }

    /**
     * Test the case when start date is after the start date of a conflicting
     * booking, and end date is on the end date. The conflicting booking should
     * be returned.
     * 
     * @throws ParseException
     */
    @Test
    public void returnsBookingIfStartAfterStartAndEndEqualsEnd() throws ParseException {
        // Act.
        List<Spot> result = user.getConflictingSpots(dateFormat.parse("16-11-2020 19:45:00"), 15);

        // Assert.
        assertEquals(1, result.size());
    }

    /**
     * Test the case when start date is after the start date of a conflicting
     * booking, and end date is after the end date. The conflicting booking
     * should be returned.
     * 
     * @throws ParseException
     */
    @Test
    public void returnsBookingIfStartAfterStartAndEndAfterEnd() throws ParseException {
        // Act.
        List<Spot> result = user.getConflictingSpots(dateFormat.parse("16-11-2020 19:45:00"), 30);

        // Assert.
        assertEquals(1, result.size());
    }

    /**
     * Test the case when start date is on the end date of a conflicting
     * booking, and end date is after the end date. The conflicting booking
     * should be returned.
     * 
     * @throws ParseException
     */
    @Test
    public void returnsBookingIfStartEqualsEndAndEndAfterEnd() throws ParseException {
        // Act.
        List<Spot> result = user.getConflictingSpots(dateFormat.parse("16-11-2020 20:00:00"), 30);

        // Assert.
        assertEquals(1, result.size());
    }

    /**
     * Test the case when start date is after the end date of a conflicting
     * booking, and end date is after the end date. No conflicting bookings
     * should be returned.
     * 
     * @throws ParseException
     */
    @Test
    public void returnsNothingIfStartAfterEndAndEndAfterEnd() throws ParseException {
        // Act.
        List<Spot> result = user.getConflictingSpots(dateFormat.parse("16-11-2020 20:30:00"), 30);

        // Assert.
        assertEquals(0, result.size());
    }

    /**
     * Test the case when start date is after the start date of a conflicting
     * booking, and end date is before the end date. The conflicting booking
     * should be returned.
     * 
     * @throws ParseException
     */
    @Test
    public void returnsBookingIfStartAfterStartAndEndBeforeEnd() throws ParseException {
        // Act.
        List<Spot> result = user.getConflictingSpots(dateFormat.parse("16-11-2020 19:40:00"), 10);

        // Assert.
        assertEquals(1, result.size());
    }
}
