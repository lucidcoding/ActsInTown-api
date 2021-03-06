package uk.co.luciditysoftware.actsintown.api.mappers.parametersetmappers.user;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.commons.codec.binary.Base64;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import uk.co.luciditysoftware.actsintown.api.requests.user.ResetPasswordRequest;
import uk.co.luciditysoftware.actsintown.domain.parametersets.user.ResetPasswordParameterSet;

@Service
public class ResetPasswordParameterSetMapperImpl implements ResetPasswordParameterSetMapper {
    private static final int HASHING_ROUNDS = 10;
    
    public ResetPasswordParameterSet map(ResetPasswordRequest request) throws NoSuchAlgorithmException  {
        ResetPasswordParameterSet parameterSet = new ResetPasswordParameterSet();
        parameterSet.setPasswordResetToken(request.getPasswordResetToken());
        setEncryptPassword(request, parameterSet);
        return parameterSet;
    }

    private void setEncryptPassword(ResetPasswordRequest request, ResetPasswordParameterSet parameterSet) throws NoSuchAlgorithmException {
        String salt = BCrypt.gensalt(HASHING_ROUNDS, SecureRandom.getInstanceStrong());
        byte[] encryptedPasswordBytes = BCrypt.hashpw(request.getPassword(), salt).getBytes();
        String encryptedPassword = Base64.encodeBase64String(encryptedPasswordBytes);    
        parameterSet.setPasswordSalt(salt);
        parameterSet.setPassword(encryptedPassword);
    }
}
