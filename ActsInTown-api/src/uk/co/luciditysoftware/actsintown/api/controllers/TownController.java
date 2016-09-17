package uk.co.luciditysoftware.actsintown.api.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import uk.co.luciditysoftware.actsintown.api.datatransferobjects.TownDto;
import uk.co.luciditysoftware.actsintown.domain.entities.Town;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.TownRepository;

@RestController
@RequestMapping("/town")
public class TownController {
	
	@Autowired
	private TownRepository townRepository; 

	@Autowired
	private ModelMapper modelMapper;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public List<TownDto> get() {
		List<Town> towns = townRepository.getAll();

		List<TownDto> townDtos = towns
			.stream()
			.map(town -> modelMapper.map(town, TownDto.class))
			.collect(Collectors.toList());
		
		return townDtos;
	}
}
