package uk.co.luciditysoftware.actsintown.api.controllers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import uk.co.luciditysoftware.actsintown.api.config.UserPrincipal;
import uk.co.luciditysoftware.actsintown.api.datatransferobjects.SpotDto;
import uk.co.luciditysoftware.actsintown.api.requests.spot.AddRequest;
import uk.co.luciditysoftware.actsintown.domain.entities.Spot;
import uk.co.luciditysoftware.actsintown.domain.parametersets.spot.AddParameterSet;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.SpotRepository;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.TownRepository;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.UserRepository;

@RestController
@RequestMapping("/spot")
public class SpotController {

	@Autowired
	private SpotRepository spotRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TownRepository townRepository;

	@RequestMapping(value = "/for-current-user", method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public List<SpotDto> getForCurrentUser() {
		UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		String username = userPrincipal.getUsername();
		Collection<Spot> spots = spotRepository.getByUsername(username);

		// https://manosnikolaidis.wordpress.com/2015/08/25/jackson-without-annotations/
		// http://www.baeldung.com/jackson-custom-serialization
		ObjectMapper mapper = new ObjectMapper();

		mapper.registerModule(new ParameterNamesModule());
		// mapper.setVisibility(forMethod, visibility);

		/*
		 * try { String spotsJason = mapper.writeValueAsString(spots); } catch
		 * (JsonProcessingException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

		ModelMapper modelMapper = new ModelMapper();

		modelMapper
			.getConfiguration()
			.setFieldMatchingEnabled(true)
			.setMethodAccessLevel(AccessLevel.PRIVATE);

		List<SpotDto> spotDtos = spots
			.stream()
			.map(spot -> modelMapper.map(spot, SpotDto.class))
			.collect(Collectors.toList());

		return spotDtos;
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public ResponseEntity<Void> Add(@RequestBody AddRequest request) {
		UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		String username = userPrincipal.getUsername();

		AddParameterSet parameterSet = new AddParameterSet() {
			{
				this.setUser(userRepository.getByUsername(username));
				this.setScheduledFor(request.getScheduledFor());
				this.setDurationMinutes(request.getDurationMinutes());
				this.setTown(townRepository.getById(request.getTownId()));
				this.setVenueName(request.getVenueName());
			}
		};

		Spot spot = Spot.add(parameterSet);
		spotRepository.save(spot);
		return new ResponseEntity<Void>(new HttpHeaders(), HttpStatus.CREATED);
	}
}
