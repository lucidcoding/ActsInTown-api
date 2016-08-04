package uk.co.luciditysoftware.actsintown.api.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import uk.co.luciditysoftware.actsintown.api.config.UserPrincipal;

//http://stackoverflow.com/questions/7462202/spring-json-request-getting-406-not-acceptable

@RestController
@RequestMapping("/test")
public class TestController {
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public String get() {

	    @SuppressWarnings("unused")
	    Object auth = SecurityContextHolder.getContext().getAuthentication();
		@SuppressWarnings("unused")
		UserPrincipal user = (UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return "TEST";
	}
}
