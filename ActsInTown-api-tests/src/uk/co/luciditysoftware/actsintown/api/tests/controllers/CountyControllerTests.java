package uk.co.luciditysoftware.actsintown.api.tests.controllers;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;

import uk.co.luciditysoftware.actsintown.api.controllers.CountyController;
import uk.co.luciditysoftware.actsintown.domain.entities.County;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.CountyRepository;

public class CountyControllerTests {

    @Mock
    private CountyRepository countyRepository; 

    @InjectMocks
    private CountyController countyController;
    
    @Before
    public void setUp() throws Exception {
         MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void canGet() {
        when(countyRepository.getAll()).thenReturn(new ArrayList<County>());
    }
}
