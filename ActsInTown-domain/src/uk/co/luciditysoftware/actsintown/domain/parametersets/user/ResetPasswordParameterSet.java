package uk.co.luciditysoftware.actsintown.domain.parametersets.user;

public class ResetPasswordParameterSet {
	private String passwordResetToken;
	private String password;
	private String passwordSalt;
	
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

	public String getPasswordSalt() {
		return passwordSalt;
	}

	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}
}
