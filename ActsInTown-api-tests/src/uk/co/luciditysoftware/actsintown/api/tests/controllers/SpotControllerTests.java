package uk.co.luciditysoftware.actsintown.api.tests.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.validation.BindingResult;

import static org.mockito.Mockito.*;

import uk.co.luciditysoftware.actsintown.api.controllers.SpotController;
import uk.co.luciditysoftware.actsintown.api.datatransferobjects.SpotDto;
import uk.co.luciditysoftware.actsintown.api.mappers.dtomappers.GenericDtoMapper;
import uk.co.luciditysoftware.actsintown.api.mappers.parametersetmappers.spot.AddParameterSetMapper;
import uk.co.luciditysoftware.actsintown.api.mappers.responsemappers.ValidationMessageMapper;
import uk.co.luciditysoftware.actsintown.api.requests.spot.AddRequest;
import uk.co.luciditysoftware.actsintown.api.utilities.CurrentUserResolver;
import uk.co.luciditysoftware.actsintown.api.utilities.RequestLogger;
import uk.co.luciditysoftware.actsintown.domain.common.ValidationMessage;
import uk.co.luciditysoftware.actsintown.domain.entities.Spot;
import uk.co.luciditysoftware.actsintown.domain.entities.Town;
import uk.co.luciditysoftware.actsintown.domain.enums.BookedState;
import uk.co.luciditysoftware.actsintown.domain.parametersets.spot.AddParameterSet;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.SpotRepository;

public class SpotControllerTests {

    @Mock
    private SpotRepository spotRepository;

    @Mock
    private AddParameterSetMapper addParameterSetMapper;

    @Mock
    private RequestLogger requestLogger;
        
    @Mock
    private CurrentUserResolver currentUserResolver;

    @Mock
    private GenericDtoMapper genericDtoMapper;

    @Mock
    private ValidationMessageMapper validationMessageMapper;

    @Mock
    private JavaMailSender mailSender;
    
    @InjectMocks
    private SpotController spotController;

    private SimpleDateFormat dateFormat;
    
    public SpotControllerTests() {
        dateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
    }

    @Before
    public void setUp() throws Exception {
         MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void getForCurrentUserCallsCorrectMethods() {
        String username = "test.user@test.com";
        when(currentUserResolver.getUsername()).thenReturn(username);
        List<Spot> spots = new ArrayList<Spot>();
        when(spotRepository.getByUsername(username)).thenReturn(spots);
        when(genericDtoMapper.map(spots, SpotDto.class)).thenReturn(new ArrayList<SpotDto>());
        spotController.getForCurrentUser();
        verify(currentUserResolver, times(1)).getUsername();
        verify(spotRepository, times(1)).getByUsername(username);
        verify(genericDtoMapper, times(1)).map(spots, SpotDto.class);
    }
    
    @Test
    public void addCallsCorrectMethods() throws ParseException {
        AddRequest request = new AddRequest();
        
        AddParameterSet parameterSet = new AddParameterSet(){{
            setScheduledFor(dateFormat.parse("16-11-2025 00:00:00"));
            setDurationMinutes(10);
            setTown(new Town());
            setVenueName("Test Venue");
            setDescription("Test Description");
            setBookedState(BookedState.AVAILABLE);
            
        }};

        BindingResult bindingResult = mock(BindingResult.class);
        when(validationMessageMapper.map(any(BindingResult.class))).thenReturn(new ArrayList<ValidationMessage>());
        when(addParameterSetMapper.map(request)).thenReturn(parameterSet);
        ResponseEntity<?> response = spotController.add(request, bindingResult);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(validationMessageMapper, times(1)).map(any(BindingResult.class));
        verify(addParameterSetMapper, times(1)).map(any(AddRequest.class));
        verify(spotRepository, times(1)).save(any(Spot.class));
    }

    @Test
    public void searchCallsCorrectMethods() throws ParseException {
        Date startDate = dateFormat.parse("16-11-2025 00:00:00");
        Date endDate = dateFormat.parse("16-11-2025 00:00:00");
        UUID townId = UUID.randomUUID();
        BookedState bookedState = BookedState.AVAILABLE;
        List<Spot> spots = new ArrayList<Spot>();
        when(spotRepository.search(eq(startDate), any(Date.class), eq(townId), eq(bookedState))).thenReturn(spots);
        when(genericDtoMapper.map(spots, SpotDto.class)).thenReturn(new ArrayList<SpotDto>());
        spotController.search(startDate, endDate, townId, bookedState);
        verify(spotRepository, times(1)).search(eq(startDate), any(Date.class), eq(townId), eq(bookedState));
        verify(genericDtoMapper, times(1)).map(spots, SpotDto.class);
    }
}
