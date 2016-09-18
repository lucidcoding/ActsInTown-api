package uk.co.luciditysoftware.actsintown.api.controllers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import uk.co.luciditysoftware.actsintown.api.config.UserPrincipal;
import uk.co.luciditysoftware.actsintown.api.datatransferobjects.SpotDto;
import uk.co.luciditysoftware.actsintown.api.mappers.parametersetmappers.spot.AddParameterSetMapper;
import uk.co.luciditysoftware.actsintown.api.requests.spot.AddRequest;
import uk.co.luciditysoftware.actsintown.domain.entities.Spot;
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

	@RequestMapping(value = "/for-current-user", method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public List<SpotDto> getForCurrentUser() {
		UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder
			.getContext()
			.getAuthentication()
			.getPrincipal();

		String username = userPrincipal.getUsername();
		Collection<Spot> spots = spotRepository.getByUsername(username);

		List<SpotDto> spotDtos = spots.stream().map(spot -> modelMapper.map(spot, SpotDto.class))
				.collect(Collectors.toList());

		return spotDtos;
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public ResponseEntity<Void> Add(@RequestBody AddRequest request) {
		AddParameterSet parameterSet = addParameterSetMapper.map(request);
		Spot spot = Spot.add(parameterSet);
		spotRepository.save(spot);
		return new ResponseEntity<Void>(new HttpHeaders(), HttpStatus.CREATED);
	}
}
