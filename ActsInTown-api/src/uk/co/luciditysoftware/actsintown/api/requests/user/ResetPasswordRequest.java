package uk.co.luciditysoftware.actsintown.api.requests.user;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

import uk.co.luciditysoftware.actsintown.api.validators.FieldMatch;

@FieldMatch.List({
    @FieldMatch(first = "password", second = "newPassword", message = "The password fields must match" )
})
public class ResetPasswordRequest {

    @NotNull(message = "This field is required")
    @Email
    private String username;

    @NotNull(message = "This field is required")
    private String passwordResetToken;

    @NotNull(message = "This field is required")
    private String password;
    
    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordResetToken() {
        return passwordResetToken;
    }

    public void setPasswordResetToken(String passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
