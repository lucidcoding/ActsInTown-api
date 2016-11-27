package uk.co.luciditysoftware.actsintown.api.mappers.parametersetmappers.user;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.commons.codec.binary.Base64;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import uk.co.luciditysoftware.actsintown.api.requests.user.ChangePasswordRequest;
import uk.co.luciditysoftware.actsintown.domain.parametersets.user.ChangePasswordParameterSet;

@Service
public class ChangePasswordParameterSetMapperImpl implements ChangePasswordParameterSetMapper{
	private static final int HASHING_ROUNDS = 10;
	
	public ChangePasswordParameterSet map(ChangePasswordRequest request) throws NoSuchAlgorithmException {
		ChangePasswordParameterSet parameterSet = new ChangePasswordParameterSet();
		setEncryptPassword(request, parameterSet);
		return parameterSet;
	}
	
	private void setEncryptPassword(ChangePasswordRequest request, ChangePasswordParameterSet parameterSet) throws NoSuchAlgorithmException {
		String salt = BCrypt.gensalt(HASHING_ROUNDS, SecureRandom.getInstanceStrong());
		byte[] encryptedPasswordBytes = BCrypt.hashpw(request.getNewPassword(), salt).getBytes();
		String encryptedPassword = Base64.encodeBase64String(encryptedPasswordBytes);	
		parameterSet.setNewPasswordSalt(salt);
		parameterSet.setNewPassword(encryptedPassword);
	}
}
