package uk.co.luciditysoftware.actsintown.api.datatransferobjects;

import java.util.UUID;

import uk.co.luciditysoftware.actsintown.domain.entities.Role;

public class UserDto {
	public UUID id;
	public String username;
	public String firstName;
	public String lastName;
	public String email;
	//public Role role;
	public String stageName;
	public boolean profilePictureUploaded;
	public String imageUrl;
}
