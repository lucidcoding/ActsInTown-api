package uk.co.luciditysoftware.actsintown.api.tests.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;

import uk.co.luciditysoftware.actsintown.api.controllers.SpotController;
import uk.co.luciditysoftware.actsintown.api.datatransferobjects.SpotDto;
import uk.co.luciditysoftware.actsintown.api.mappers.dtomappers.GenericDtoMapper;
import uk.co.luciditysoftware.actsintown.api.mappers.parametersetmappers.spot.AddParameterSetMapper;
import uk.co.luciditysoftware.actsintown.api.mappers.responsemappers.ValidationMessageMapper;
import uk.co.luciditysoftware.actsintown.api.utilities.CurrentUserResolver;
import uk.co.luciditysoftware.actsintown.api.utilities.RequestLogger;
import uk.co.luciditysoftware.actsintown.domain.entities.Spot;
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
    
    @Before
    public void setUp() throws Exception {
         MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void canGetForCurrentUser() {
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
}
