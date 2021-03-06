package uk.co.luciditysoftware.actsintown.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import uk.co.luciditysoftware.actsintown.api.datatransferobjects.CountyDto;
import uk.co.luciditysoftware.actsintown.api.mappers.dtomappers.GenericDtoMapper;
import uk.co.luciditysoftware.actsintown.domain.entities.County;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.CountyRepository;

/**
 * Controller class that handles all use cases performing actions on the county entity.
 * @author Paul Davies
 */
@RestController
@RequestMapping("/county")
public class CountyController {

    @Autowired
    private CountyRepository countyRepository; 

    @Autowired
    private GenericDtoMapper genericDtoMapper;
    
    /**
     * Returns all counties.
     * @return List of counties
     * @throws Exception 
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public List<CountyDto> get() {
        List<County> counties = countyRepository.getAll();
        List<CountyDto> countyDtos = genericDtoMapper.map(counties, CountyDto.class);    
        return countyDtos;
    }
}
