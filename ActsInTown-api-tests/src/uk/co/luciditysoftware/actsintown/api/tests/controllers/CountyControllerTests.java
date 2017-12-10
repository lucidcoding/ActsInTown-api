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
    public void canGet() {
        /* @SuppressWarnings("serial")
        List<County> counties = new ArrayList<County>() {{
            new County() {{
                setId(UUID.randomUUID());
                setName("Test County 01");
            }};
            new County() {{
                setId(UUID.randomUUID());
                setName("Test County 02");
            }};;
        }};*/
        List<County> counties = new ArrayList<County>();
        when(countyRepository.getAll()).thenReturn(counties);
        when(genericDtoMapper.map(counties, CountyDto.class)).thenReturn(new ArrayList<CountyDto>());
        countyController.get(); 
        // assertEquals(2, countyDtos.size());
        verify(countyRepository, times(1)).getAll();
        verify(genericDtoMapper, times(1)).map(counties, CountyDto.class);
    }
}
