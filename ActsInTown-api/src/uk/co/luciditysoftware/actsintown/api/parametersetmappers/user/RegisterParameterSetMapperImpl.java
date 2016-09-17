package uk.co.luciditysoftware.actsintown.api.parametersetmappers.user;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import uk.co.luciditysoftware.actsintown.api.requests.user.RegisterRequest;
import uk.co.luciditysoftware.actsintown.domain.parametersets.user.RegisterParameterSet;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.RoleRepository;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.UserTypeRepository;

@Service
public class RegisterParameterSetMapperImpl implements RegisterParameterSetMapper {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserTypeRepository userTypeRepository;

	private static final int HASHING_ROUNDS = 10;

	public RegisterParameterSet map(RegisterRequest request) throws NoSuchAlgorithmException {
		RegisterParameterSet parameterSet = new RegisterParameterSet() {
			{
				this.setUsername(request.getUsername());
				this.setFirstName(request.getFirstName());
				this.setLastName(request.getLastName());
				this.setRole(roleRepository.getById(UUID.fromString("2C6E33B8-BD7C-492C-807D-B4B1BCAE5F4F")));
				this.setUserTypes(userTypeRepository.getByIds(request.getUserTypeIds()));
				this.setStageName(request.getStageName());
			}
		};

		setEncryptPassword(request, parameterSet);
		return parameterSet;
	}
	
	private void setEncryptPassword(RegisterRequest request, RegisterParameterSet parameterSet) throws NoSuchAlgorithmException {
		String salt = BCrypt.gensalt(HASHING_ROUNDS, SecureRandom.getInstanceStrong());
		byte[] encryptedPasswordBytes = BCrypt.hashpw(request.getPassword(), salt).getBytes();
		String encryptedPassword = Base64.encodeBase64String(encryptedPasswordBytes);	
		parameterSet.setPasswordSalt(salt);
		parameterSet.setPassword(encryptedPassword);
	}
}
