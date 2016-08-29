package uk.co.luciditysoftware.actsintown.domain.entities;

import java.util.UUID;

import uk.co.luciditysoftware.actsintown.domain.common.Entity;
import uk.co.luciditysoftware.actsintown.domain.parametersets.user.RegisterParameterSet;

public class User extends Entity {
	private String username;
	private String password;
	private String passwordSalt;
	private String firstName;
	private String lastName;
	private String email;
	private boolean enabled;
	private Role role;
	
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

	public String getPasswordSalt() {
		return passwordSalt;
	}

	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
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

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public static User register(RegisterParameterSet parameterSet) {
		User user = new User();
		user.id = UUID.randomUUID();
		user.username = parameterSet.getUsername();
		user.password = parameterSet.getPassword();
		user.passwordSalt = parameterSet.getPasswordSalt();
		user.firstName = parameterSet.getFirstName();
		user.lastName = parameterSet.getLastName();
		user.email = parameterSet.getUsername();
		user.enabled = true;
		user.role = parameterSet.getRole();
		return user;
	}
}
