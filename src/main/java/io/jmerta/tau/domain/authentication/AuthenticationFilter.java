package io.jmerta.tau.domain.authentication;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger LOGGER = Logger.getLogger( AuthenticationFilter.class.getName() );

    private AuthManager authManager() {
        return (AuthManager) getAuthenticationManager();
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (!(req instanceof HttpServletRequest)) {
            chain.doFilter(req, res);
            return;
        }

        HttpServletRequest httpRequest = (HttpServletRequest) req;

        if (httpRequest.getPathInfo() == null) {
            chain.doFilter(req, res);
            return;
        }

        if (!this.needsAuthentication(httpRequest.getPathInfo())) {
            chain.doFilter(req, res);
            return;
        }

        try {
            httpRequest.getAuthType();
            String token = null;
            Cookie[] cookies = ((HttpServletRequest) req).getCookies();
            if (null == cookies) {
                throw new UsernameNotFoundException("Missing cookie");
            }
            for (int i = 0; i < cookies.length; i++) {
                if (authManager().tokenName.equals(cookies[i].getName())) {
                    token = cookies[i].getValue();
                    cookies[i].setHttpOnly(true);
                    cookies[i].setMaxAge( 60 * 60);
                    cookies[i].setPath("/");
                    ((HttpServletResponse) res).addCookie(cookies[i]);

                }
            }
            if (null == token) {
                throw new UsernameNotFoundException("Missing cookie");
            }

            Authentication authentication = authManager().authenticate(token);
            authManager().persistSession(authentication, token);
            chain.doFilter(req, res);
        } catch (AuthenticationException ex) {
            Logger.logMsg(ex);
        }
    }

    private boolean needsAuthentication(String pathInfo) {
        if (pathInfo.equals("/")) {
            return false;
        }
        if (Pattern.compile("^/api/").matcher(pathInfo).find()) {
            return false;
        }
        return false;
    }

}
