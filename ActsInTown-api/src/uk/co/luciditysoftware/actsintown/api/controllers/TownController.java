package uk.co.luciditysoftware.actsintown.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import uk.co.luciditysoftware.actsintown.domain.entities.County;
import uk.co.luciditysoftware.actsintown.domain.entities.Town;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.TownRepository;

@RestController
@RequestMapping("/town")
public class TownController {
	
	@Autowired
	private TownRepository townRepository; 
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public List<Town> get() {

		List<Town> towns = townRepository.getAll();
		County aCounty = towns.get(0).getCounty();
	    /*@SuppressWarnings("unused")
	    Object auth = SecurityContextHolder.getContext().getAuthentication();
		@SuppressWarnings("unused")
		UserPrincipal user = (UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();*/
		return towns;
	}
}