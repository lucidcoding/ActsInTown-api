package uk.co.luciditysoftware.actsintown.api.tests.controllers;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import uk.co.luciditysoftware.actsintown.api.controllers.CountyController;
import uk.co.luciditysoftware.actsintown.api.datatransferobjects.CountyDto;
import uk.co.luciditysoftware.actsintown.api.mappers.dtomappers.GenericDtoMapper;
import uk.co.luciditysoftware.actsintown.domain.entities.County;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.CountyRepository;

public class CountyControllerTests {

    @Mock
    private CountyRepository countyRepository; 

    @Mock
    private GenericDtoMapper genericDtoMapper;
    
    @InjectMocks
    private CountyController countyController;
    
    @Before
    public void setUp() throws Exception {
         MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void getCallsCorrectMethods() {
        List<County> counties = new ArrayList<County>();
        when(countyRepository.getAll()).thenReturn(counties);
        when(genericDtoMapper.map(counties, CountyDto.class)).thenReturn(new ArrayList<CountyDto>());
        countyController.get();
        verify(countyRepository, times(1)).getAll();
        verify(genericDtoMapper, times(1)).map(counties, CountyDto.class);
    }
}
