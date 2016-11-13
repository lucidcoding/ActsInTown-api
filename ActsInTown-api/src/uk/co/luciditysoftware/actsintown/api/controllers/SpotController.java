package uk.co.luciditysoftware.actsintown.api.controllers;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import uk.co.luciditysoftware.actsintown.api.datatransferobjects.SpotDto;
import uk.co.luciditysoftware.actsintown.api.mappers.parametersetmappers.spot.AddParameterSetMapper;
import uk.co.luciditysoftware.actsintown.api.requests.spot.AddRequest;
import uk.co.luciditysoftware.actsintown.api.services.RequestLogger;
import uk.co.luciditysoftware.actsintown.domain.entities.Spot;
import uk.co.luciditysoftware.actsintown.domain.enums.BookedState;
import uk.co.luciditysoftware.actsintown.domain.parametersets.spot.AddParameterSet;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.SpotRepository;

@RestController
@RequestMapping("/spot")
public class SpotController {

	@Autowired
	private SpotRepository spotRepository;

	@Autowired
	private AddParameterSetMapper addParameterSetMapper;
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private RequestLogger requestLogger;
	
	@RequestMapping(value = "/for-current-user", method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public List<SpotDto> getForCurrentUser() {
		String username = (String) SecurityContextHolder
			.getContext()
			.getAuthentication()
			.getPrincipal();

		Collection<Spot> spots = spotRepository.getByUsername(username);

		List<SpotDto> spotDtos = spots.stream().map(spot -> modelMapper.map(spot, SpotDto.class))
				.collect(Collectors.toList());

		return spotDtos;
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public ResponseEntity<Void> Add(@RequestBody AddRequest request) {
		requestLogger.log(request);
		AddParameterSet parameterSet = addParameterSetMapper.map(request);
		Spot spot = Spot.add(parameterSet);
		spotRepository.save(spot);
		return new ResponseEntity<Void>(new HttpHeaders(), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public List<SpotDto> search(
			@RequestParam("startDate") @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") Date startDate, 
			@RequestParam("endDate") @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") Date endDate,
			@RequestParam("townId") UUID townId,
			@RequestParam("bookedState") BookedState bookedState) {
		
		GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(endDate);
        cal.add(Calendar.DAY_OF_MONTH, 1);        
        //Date scheduledEnd = cal.getTime();
        //return scheduledEnd;
        
        Collection<Spot> spots = spotRepository.search(startDate, endDate, townId, bookedState);

		List<SpotDto> spotDtos = spots.stream().map(spot -> modelMapper.map(spot, SpotDto.class))
				.collect(Collectors.toList());

		return spotDtos;
	}
}
