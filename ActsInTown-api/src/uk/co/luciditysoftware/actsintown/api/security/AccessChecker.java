package uk.co.luciditysoftware.actsintown.api.security;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;

public class AccessChecker {
    public boolean check(Authentication authentication, HttpServletRequest request, UUID id, String thing) {
        return true;
    }
}
