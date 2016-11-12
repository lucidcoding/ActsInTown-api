package uk.co.luciditysoftware.actsintown.api.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
public interface AuthenticationService extends AuthenticationProvider
{
    //@Override
    //UserPrincipal authenticate(Authentication authentication);

    void saveUser(
            @NotNull(message = "{validate.authenticate.saveUser}") @Valid
                UserPrincipal principal,
            String newPassword
    );
}
