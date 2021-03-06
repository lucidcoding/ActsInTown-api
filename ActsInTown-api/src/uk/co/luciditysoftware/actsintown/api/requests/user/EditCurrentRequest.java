package uk.co.luciditysoftware.actsintown.api.requests.user;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class EditCurrentRequest {	
	@NotNull(message = "This field is required")
	@Length(max = 50)
	private String firstName;

	@NotNull(message = "This field is required")
	@Length(max = 50)
	private String lastName;
	
	private List<UUID> userTypeIds;

	@Length(max = 50)
	private String stageName;
	
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

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}
}
