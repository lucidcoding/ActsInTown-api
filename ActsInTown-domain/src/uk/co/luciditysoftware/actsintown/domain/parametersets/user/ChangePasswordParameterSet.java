package uk.co.luciditysoftware.actsintown.domain.parametersets.user;

public class ChangePasswordParameterSet {
	private String newPassword;
	private String newPasswordSalt;
	
	public String getNewPassword() {
		return newPassword;
	}
	
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	public String getNewPasswordSalt() {
		return newPasswordSalt;
	}
	
	public void setNewPasswordSalt(String newPasswordSalt) {
		this.newPasswordSalt = newPasswordSalt;
	}
}
