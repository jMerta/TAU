package io.jmerta.tau.domain.accountManagment.util;

import io.jmerta.tau.domain.accountManagment.entity.Account;
import io.jmerta.tau.domain.accountManagment.entity.Session;
import io.jmerta.tau.domain.accountManagment.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class AuthManager implements AuthenticationManager {

    @Value("${security.token.name}")
    public String tokenName;

    private AccountService accountService;

    @Autowired
    public AuthManager(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }

    public Authentication authentication(Account accountFront){
        Account accountBackend = accountService.loadUserByUsername(accountFront.getUsername());


        if (accountBackend.getPassword().equalsIgnoreCase(accountFront.getPassword())){
            Authentication authentication = new UsernamePasswordAuthenticationToken(accountBackend,null,accountBackend.getAuthorities());
            return this.authenticate(authentication);
        }


        throw new BadCredentialsException("Bad credentials");
    }


    public Authentication authenticate(String token) {
        Account account = accountService.loadUserByToken(token);
        if (null == account) {
            throw new UsernameNotFoundException("User not found");
        }

        if (!account.isCredentialsNonExpired()){
            throw new SessionAuthenticationException("Session expired");
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(account,null, account.getAuthorities());
        return authenticate(authentication);
    }

    public void persistSession(Authentication authentication, String token){
        Account account = (Account) authentication.getPrincipal();
        Session session = new Session();
        account.setSession(session);
        account.getSession().setToken(token);
        accountService.saveSession(account,token);
    }

}
