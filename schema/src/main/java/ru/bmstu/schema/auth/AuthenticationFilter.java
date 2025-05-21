package ru.bmstu.schema.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.connector.Request;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private Request httpServletRequest;

    AuthenticationFilter(final RequestMatcher requiresAuth) {
        super(requiresAuth);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        String token = request.getHeader("Authorization");
        if (token != null) {
            token = StringUtils.removeStart(token, "Bearer").trim();
        }
        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(token, null);
        return getAuthenticationManager().authenticate(authRequest);
    }

    protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response,
                                            final FilterChain chain, final Authentication authResult)
            throws IOException, ServletException, javax.servlet.ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter((ServletRequest) request, (ServletResponse) response);
    }
}
