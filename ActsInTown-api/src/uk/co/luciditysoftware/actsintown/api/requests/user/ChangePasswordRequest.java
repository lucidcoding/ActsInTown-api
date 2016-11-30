package uk.co.luciditysoftware.actsintown.api.requests.user;

import javax.validation.constraints.NotNull;

import uk.co.luciditysoftware.actsintown.api.validators.FieldMatch;
@FieldMatch.List({
    @FieldMatch(first = "newPassword", second = "confirmNewPassword", message = "The password fields must match" )
})
public class ChangePasswordRequest {
    @NotNull(message = "This field is required")
    private String oldPassword;
    
    @NotNull(message = "This field is required")
    private String newPassword;

    private String confirmNewPassword;

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
