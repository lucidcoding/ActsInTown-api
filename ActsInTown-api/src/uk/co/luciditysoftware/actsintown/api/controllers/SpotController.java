package uk.co.luciditysoftware.actsintown.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import uk.co.luciditysoftware.actsintown.domain.entities.Spot;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.SpotRepository;

@RestController
@RequestMapping("/spot")
public class SpotController {

	@Autowired
	private SpotRepository spotRepository; 
	
	@RequestMapping(value = "/for-current-user", method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public List<Spot> getForCurrentUser() {
		String username = "gary@green.com";
		List<Spot> spots = spotRepository.getByUsername(username);
		//County aCounty = towns.get(0).getCounty();
	    /*@SuppressWarnings("unused")
	    Object auth = SecurityContextHolder.getContext().getAuthentication();
		@SuppressWarnings("unused")
		UserPrincipal user = (UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();*/
		return spots;
	}
}