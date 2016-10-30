package uk.co.luciditysoftware.actsintown.domain.parametersets.user;

import java.util.Collection;

import uk.co.luciditysoftware.actsintown.domain.entities.UserType;

public class EditParameterSet {
	private String firstName;
	private String lastName;
	private Collection<UserType> userTypes;
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
	
	public Collection<UserType> getUserTypes() {
		return userTypes;
	}
	
	public void setUserTypes(Collection<UserType> userTypes) {
		this.userTypes = userTypes;
	}
	
	public String getStageName() {
		return stageName;
	}
	
	public void setStageName(String stageName) {
		this.stageName = stageName;
	}
}
