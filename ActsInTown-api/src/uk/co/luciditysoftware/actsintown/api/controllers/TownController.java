package uk.co.luciditysoftware.actsintown.api.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import uk.co.luciditysoftware.actsintown.api.datatransferobjects.TownDto;
import uk.co.luciditysoftware.actsintown.api.mappers.dtomappers.GenericDtoMapper;
import uk.co.luciditysoftware.actsintown.domain.entities.Town;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.TownRepository;

/**
 * Controller class that handles all use cases performing actions on the town entity.
 * @author Paul Davies
 */
@RestController
@RequestMapping("/town")
public class TownController {
    
    @Autowired
    private TownRepository townRepository; 

    @Autowired
    private GenericDtoMapper genericDtoMapper;
    
    /**
     * Returns all towns.
     * @return List of towns
     * @throws Exception 
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public List<TownDto> get() {
        List<Town> towns = townRepository.getAll();
        List<TownDto> townDtos = genericDtoMapper.map(towns, TownDto.class);    
        return townDtos;
    }

    /**
     * Returns all towns for the specified county.
     * @return List of towns
     * @throws Exception 
     */
    @RequestMapping(value = "/for-county/{countyId}", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public List<TownDto> getForCounty(@PathVariable UUID countyId) {
        List<Town> towns = townRepository.getByCountyId(countyId);
        List<TownDto> townDtos = genericDtoMapper.map(towns, TownDto.class);    
        return townDtos;
    }
}
