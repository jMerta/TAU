package io.jmerta.tau.domain.authentication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletResponse;

public class AuthManager implements AuthenticationManager {

    @Value("${security.token.name}")
    public String tokenName;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }

    public Authentication authentication(User user){
        //Authenticate user on login page
    }


    public Authentication authenticate(String token) {

        //load uuser by token
        return new UsernamePasswordAuthenticationToken(token);
    }

    public void persistSession(Authentication authentication, String token) {
    }


    public static void handle(HttpServletResponse res, AuthenticationException ex) {
    }

}
