package uk.co.luciditysoftware.actsintown.api.requests.user;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import uk.co.luciditysoftware.actsintown.api.validators.FieldMatch;

@FieldMatch.List({
    @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match" )
})
public class RegisterRequest {

	@NotNull(message = "This field is required")
	@Email
	private String username;

	@NotNull(message = "This field is required")
	private String password;

	@NotNull(message = "This field is required")
	@Length(max = 50)
	private String firstName;

	@NotNull(message = "This field is required")
	@Length(max = 50)
	private String lastName;
	
	private List<UUID> userTypeIds;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<UUID> getUserTypeIds() {
		return userTypeIds;
	}

	public void setUserTypeIds(List<UUID> userTypeIds) {
		this.userTypeIds = userTypeIds;
	}
}
