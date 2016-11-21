package uk.co.luciditysoftware.actsintown.domain.entities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import uk.co.luciditysoftware.actsintown.domain.common.Entity;
import uk.co.luciditysoftware.actsintown.domain.common.ValidationMessage;
import uk.co.luciditysoftware.actsintown.domain.common.ValidationMessageType;
import uk.co.luciditysoftware.actsintown.domain.parametersets.user.ChangePasswordParameterSet;
import uk.co.luciditysoftware.actsintown.domain.parametersets.user.EditParameterSet;
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
	private Collection<Spot> spots;
	private Collection<UserUserType> userUserTypes;
	private String stageName;
	private String verificationToken;
	private Date verificationTokenExpiry;
	private boolean verified;
	private boolean profilePictureUploaded;
	private String passwordResetToken;
	private Date passwordResetTokenExpiry;
	
	public List<Spot> getConflictingSpots(Date scheduledFor, int durationMinutes) {
		List<Spot> conflictingSpots = new ArrayList<Spot>();
		GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(scheduledFor);
        cal.add(Calendar.MINUTE, durationMinutes);        
        Date scheduledEnd = cal.getTime();
        
		for (Spot spot : spots) {
			if ((scheduledEnd.after(spot.getScheduledFor()) || scheduledEnd.equals(spot.getScheduledFor()))
					&& (scheduledEnd.before(spot.getScheduledEnd()) || scheduledEnd.equals(spot.getScheduledEnd()))) {
				conflictingSpots.add(spot);
			} else if ((scheduledFor.after(spot.getScheduledFor()) || scheduledFor.equals(spot.getScheduledFor()))
					&& (scheduledFor.before(spot.getScheduledEnd()) || scheduledFor.equals(spot.getScheduledEnd()))) {
				conflictingSpots.add(spot);
			}
		}

		return conflictingSpots;
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
		user.stageName = parameterSet.getStageName();
		
		user.userUserTypes = parameterSet
			.getUserTypes()
			.stream()
			.map(userType -> new UserUserType() {{
				this.setId(UUID.randomUUID());
				this.setUser(user);
				this.setUserType(userType);
			}})
			.collect(Collectors.toList());
		
		user.generateVerificationToken();
		return user;
	}

	//http://www.baeldung.com/registration-verify-user-by-email
	public void generateVerificationToken() {
		this.verified = false;
		this.verificationToken = UUID.randomUUID().toString();     
        this.verificationTokenExpiry = getTimeIn24Hours();
	}
	
	public List<ValidationMessage> validateVerify() {
		List<ValidationMessage> validationMessages = new ArrayList<ValidationMessage>();
		Date now = new Date();
		
		if(this.verified) {
			validationMessages.add(new ValidationMessage(){
				{
					setType(ValidationMessageType.ERROR);
					setField(null);
					setText("User is already verified.");
				}
			});
			
			return validationMessages;
		}
		
		if(now.after(this.verificationTokenExpiry)) {
			validationMessages.add(new ValidationMessage(){
				{
					setType(ValidationMessageType.ERROR);
					setField(null);
					setText("Vaerification token has expired.");
				}
			});
		}
		
		return validationMessages;
	}
	
	public void edit(EditParameterSet parameterSet) {
		this.firstName = parameterSet.getFirstName();
		this.lastName = parameterSet.getLastName();
		this.firstName = parameterSet.getFirstName();
		this.stageName = parameterSet.getStageName();
		
		List<UserType> existingUserTypes = this
			.userUserTypes
			.stream()
			.map(userUserType -> userUserType.getUserType())
			.collect(Collectors.toList());
		
		for(UserType userType : parameterSet.getUserTypes()) {
			if(!existingUserTypes.contains(userType)) {
				UserUserType userUserType = new UserUserType();
				userUserType.setId(UUID.randomUUID());
				userUserType.setUser(this);
				userUserType.setUserType(userType);
				this.userUserTypes.add(userUserType);
			}
		}
		
		for(UserUserType userUserType : this.userUserTypes) {
			if(!parameterSet.getUserTypes().contains(userUserType.getUserType())) {
				this.userUserTypes.remove(userUserType);
			}
		}
	}
	
	public void changePassword(ChangePasswordParameterSet parameterSet) {
		this.password = parameterSet.getNewPassword();
		this.passwordSalt = parameterSet.getNewPasswordSalt();
	}
	
	public String getImageUrl() {
		if(this.profilePictureUploaded) {
			return "https://localhost:8443/ActsInTown-uploads/ProfileImages/" + this.id.toString() + ".jpg";
		} else {
			return "/assets/svg/anonymous.svg";
		}
	}
	
	public void initializePasswordReset() {
		this.passwordResetToken = UUID.randomUUID().toString();       
        this.passwordResetTokenExpiry = getTimeIn24Hours();
	}
	
	private Date getTimeIn24Hours() {
		GregorianCalendar cal = new GregorianCalendar();
		int expirationMinutes = 60 * 24;
        cal.add(Calendar.MINUTE, expirationMinutes); 
        return cal.getTime();
	}
	
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

	public Collection<Spot> getSpots() {
		return spots;
	}

	public void setSpots(Collection<Spot> spots) {
		this.spots = spots;
	}

	public Collection<UserUserType> getUserUserTypes() {
		return userUserTypes;
	}

	public void setUserUserTypes(Collection<UserUserType> userUserTypes) {
		this.userUserTypes = userUserTypes;
	}

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}
	
	public void verify() {
		this.verified = true;
	}
	
	public String getVerificationToken() {
		return verificationToken;
	}

	public void setVerificationToken(String verificationToken) {
		this.verificationToken = verificationToken;
	}

	public Date getVerificationTokenExpiry() {
		return verificationTokenExpiry;
	}

	public void setVerificationTokenExpiry(Date verificationTokenExpiry) {
		this.verificationTokenExpiry = verificationTokenExpiry;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public boolean isProfilePictureUploaded() {
		return profilePictureUploaded;
	}

	public void setProfilePictureUploaded(boolean profilePictureUploaded) {
		this.profilePictureUploaded = profilePictureUploaded;
	}

	public String getPasswordResetToken() {
		return passwordResetToken;
	}

	public void setPasswordResetToken(String passwordResetToken) {
		this.passwordResetToken = passwordResetToken;
	}

	public Date getPasswordResetTokenExpiry() {
		return passwordResetTokenExpiry;
	}

	public void setPasswordResetTokenExpiry(Date passwordResetTokenExpiry) {
		this.passwordResetTokenExpiry = passwordResetTokenExpiry;
	}
}
