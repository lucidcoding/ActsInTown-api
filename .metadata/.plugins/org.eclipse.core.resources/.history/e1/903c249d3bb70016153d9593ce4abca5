package uk.co.luciditysoftware.actsintown.api.requests.user;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

public class InitializePasswordResetRequest {
	
	@NotNull(message = "This field is required")
	@Email
	private String username;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
}
