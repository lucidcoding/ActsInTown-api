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
import uk.co.luciditysoftware.actsintown.domain.parametersets.user.ResetPasswordParameterSet;

/**
 * Entity class representing a user in the Acts In Town system.
 * @author Paul Davies
 */
public class User extends Entity {
    
    /**
     * The username of the user
     */
    private String username;
    
    /**
     * Hash of the password for the user
     */
    private String password;
    
    /**
     * Salt for the password for the user
     */
    private String passwordSalt;
    
    /**
     * The first name of the user
     */
    private String firstName;
    
    /**
     * The last name of the user
     */
    private String lastName;
    
    /**
     * The email address of the user
     */
    private String email;
    
    /**
     * Whether this user is enabled
     */
    private boolean enabled;
    
    /**
     * The security role of the user
     */
    private Role role;
    
    /**
     * Spots added by the user
     */
    private Collection<Spot> spots;
    
    /**
     * USerUserType entities pertaining to this user
     */
    private Collection<UserUserType> userUserTypes;
    
    /**
     * The stage name for this user, if they are an act
     */
    private String stageName;
    
    /**
     * Token for verifying the user
     */
    private String verificationToken;
    
    /**
     * Expiry date for the the verification token
     */
    private Date verificationTokenExpiry;
    
    /**
     * Whether the user has been verified
     */
    private boolean verified;
    
    /**
     * Whether the user has uploaded a profile picture
     */
    private boolean profilePictureUploaded;
    
    /**
     * Token for the resetting of the password
     */
    private String passwordResetToken;
    
    /**
     * Expiry date for the password reset token
     */
    private Date passwordResetTokenExpiry;
    
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
    /**
     * Returns a list of spots that conflict with the supplied time for the user.
     * @param scheduledFor The start date and time for the period in which to find conflicting spots
     * @param durationMinutes The duration of the period in which to find conflicting spots
     * @return
     */
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
    
    /**
     * Registers a user with the values in the supplied parameter set
     * @param parameterSet Contains the values for the new user entity
     * @return the newly created user entity
     */
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

    /**
     * Creates the verification token for the user.
     */
    public void generateVerificationToken() {
        this.verified = false;
        this.verificationToken = UUID.randomUUID().toString();     
        this.verificationTokenExpiry = getTimeIn24Hours();
    }
    
    /**
     * Validates whether the user can be validated.
     * @return List of validation messages containing errors - empty if the request is valid
     */
    public List<ValidationMessage> validateVerify() {
        List<ValidationMessage> validationMessages = new ArrayList<ValidationMessage>();
        Date now = new Date();
        
        if(this.verified) {
            validationMessages.add(new ValidationMessage(
                ValidationMessageType.ERROR,
                null,
                "User is already verified."));
            
            return validationMessages;
        }
        
        if(now.after(this.verificationTokenExpiry)) {
            validationMessages.add(new ValidationMessage(
                ValidationMessageType.ERROR,
                null,
                "Verification token has expired."));
        }
        
        return validationMessages;
    }
    
    /**
     * Marks the user as verified.
     */
    public void verify() {
        this.verified = true;
    }
    
    /**
     * Edits the user with the values supplied in the parameter set.
     * @param parameterSet the values with which to update the user
     */
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
    
    /**
     * Updates the user's password with the supplied values.
     * @param parameterSet Contains values for updating the user's password.
     */
    public void changePassword(ChangePasswordParameterSet parameterSet) {
        this.password = parameterSet.getNewPassword();
        this.passwordSalt = parameterSet.getNewPasswordSalt();
    }
    
    /**
     * Returns URL for the user's profile picture.
     * @return
     */
    public String getImageUrl() {
        if(this.profilePictureUploaded) {
            return "https://localhost:8443/ActsInTown-uploads/ProfileImages/" + this.id.toString() + ".jpg";
        } else {
            return "/assets/svg/anonymous.svg";
        }
    }
    
    /**
     * Sets a token to allow the user to reset their password.
     */
    public void initializePasswordReset() {
        this.passwordResetToken = UUID.randomUUID().toString();       
        this.passwordResetTokenExpiry = getTimeIn24Hours();
    }
    
    /**
     * Validates that the values in the supplied parameter set can be used to reset the user's password.
     * @param parameterSet The parameter set containing values for resetting the password
     * @return List of validation messages containing errors - empty if parameter set is valid
     */
    public List<ValidationMessage> validateResetPassword(ResetPasswordParameterSet parameterSet) {
        List<ValidationMessage> validationMessages = new ArrayList<ValidationMessage>();
        Date now = new Date();
        
        if(this.passwordResetToken == null
                || !this.passwordResetToken.equals(parameterSet.getPasswordResetToken())
                || this.passwordResetTokenExpiry == null
                || now.after(this.passwordResetTokenExpiry)) {
            validationMessages.add(new ValidationMessage(){
                {
                    setType(ValidationMessageType.ERROR);
                    setField(null);
                    setText("Invalid verification token.");
                }
            });
        }
        
        return validationMessages;
    }
    
    /**
     * Resets the user's password with the values in the supplied parameter set.
     * @param parameterSet
     */
    public void resetPassword(ResetPasswordParameterSet parameterSet) {
        this.password = parameterSet.getPassword();
        this.passwordSalt = parameterSet.getPasswordSalt();
        this.passwordResetToken = null;
        this.passwordResetTokenExpiry = null;
    }
    
    /**
     * Returns what the date and time will be in 24 hours.
     * @return The date and time in 24 hours.
     */
    private Date getTimeIn24Hours() {
        GregorianCalendar cal = new GregorianCalendar();
        int expirationMinutes = 60 * 24;
        cal.add(Calendar.MINUTE, expirationMinutes); 
        return cal.getTime();
    }
    
    /**
     * Gets the username of the user.
     * @return The username of the user
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * Sets the username of the user.
     * @param username The username of the user
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * Gets the hash of the password for the user.
     * @return Hash of the password
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * Sets the hash of the password for the user.
     * @param password Hash of the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the salt for the password for the user
     * @return The salt for the password for the user
     */
    public String getPasswordSalt() {
        return passwordSalt;
    }

    /**
     * Sets the halt for the password for the user
     * @param passwordSalt Salt for the password
     */
    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }
    
    /**
     * Gets the first name of the user.
     * @return The first name of the user
     */
    public String getFirstName() {
        return firstName;
    }
    
    /**
     * Sets the first name of the user.
     * @param firstName The first name of the user
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the user.
     * @return The last name of the user
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets the last name of the user.
     * @param lastName The last name of the user
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the email address of the user.
     * @return The email address of the user
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * Sets the email address of the user.
     * @param email the email address of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets whether this user is enabled.
     * @return True if this user is enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets whether this user is enabled.
     * @param enabled True if this user is enabled
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Gets the security role of the user.
     * @return The security role of the user
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets the security role of the user
     * @param role The security role of the user
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Gets the spots added by the user.
     * @return Spots added by the user
     */
    public Collection<Spot> getSpots() {
        return spots;
    }

    /**
     * Sets the spots added by the user
     * @param spots spots added by the user
     */
    public void setSpots(Collection<Spot> spots) {
        this.spots = spots;
    }

    /**
     * Sets UserUserType entities pertaining to this user.
     * @return UserUserType entities pertaining to this user
     */
    public Collection<UserUserType> getUserUserTypes() {
        return userUserTypes;
    }

    /**
     * Sets userUserType entities pertaining to this user.
     * @param userUserTypes UserUserType entities pertaining to this user
     */
    public void setUserUserTypes(Collection<UserUserType> userUserTypes) {
        this.userUserTypes = userUserTypes;
    }

    /**
     * Gets the stage name for this user, if they are an act.
     * @return The stage name for this user
     */
    public String getStageName() {
        return stageName;
    }

    /**
     * Sets the stage name for this user, if they are an act.
     * @param stageName The stage name for this user
     */
    public void setStageName(String stageName) {
        this.stageName = stageName;
    }
    
    /**
     * Gets the token for verifying the user.
     * @return Token for verifying the user
     */
    public String getVerificationToken() {
        return verificationToken;
    }

    /**
     * Gets the token for verifying the user.
     * @param verificationToken Token for verifying the user
     */
    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    /**
     * Gets the expiry date for the the verification token.
     * @return Expiry date for the the verification token
     */
    public Date getVerificationTokenExpiry() {
        return verificationTokenExpiry;
    }

    /**
     * Sets the expiry date for the the verification token.
     * @param verificationTokenExpiry Expiry date for the the verification token
     */
    public void setVerificationTokenExpiry(Date verificationTokenExpiry) {
        this.verificationTokenExpiry = verificationTokenExpiry;
    }

    /**
     * Gets whether the user has been verified.
     * @return True if the user has been verified
     */
    public boolean isVerified() {
        return verified;
    }

    /**
     * Sets whether the user has been verified.
     * @param verified True if the user has been verified
     */
    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    /**
     * Gets whether the user has uploaded a profile picture.
     * @return True if the user has uploaded a profile picture
     */
    public boolean isProfilePictureUploaded() {
        return profilePictureUploaded;
    }

    /**
     * Sets whether the user has uploaded a profile picture
     * @param profilePictureUploaded True if the user has uploaded a profile picture
     */
    public void setProfilePictureUploaded(boolean profilePictureUploaded) {
        this.profilePictureUploaded = profilePictureUploaded;
    }

    /**
     * Gets the token for the resetting of the password.
     * @return Token for the resetting of the password
     */
    public String getPasswordResetToken() {
        return passwordResetToken;
    }

    /**
     * Sets the token for the resetting of the password.
     * @param passwordResetToken Token for the resetting of the password
     */
    public void setPasswordResetToken(String passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }

    /**
     * Gets the expiry date for the password reset token.
     * @return Expiry date for the password reset token
     */
    public Date getPasswordResetTokenExpiry() {
        return passwordResetTokenExpiry;
    }

    /**
     * Sets the expiry date for the password reset token.
     * @param passwordResetTokenExpiry Expiry date for the password reset token
     */
    public void setPasswordResetTokenExpiry(Date passwordResetTokenExpiry) {
        this.passwordResetTokenExpiry = passwordResetTokenExpiry;
    }
}
