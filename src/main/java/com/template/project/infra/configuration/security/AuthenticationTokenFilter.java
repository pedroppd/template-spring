package com.template.project.infra.configuration.security;

import com.template.project.infra.repositories.impl.UserRepositoryImpl;
import com.template.project.infra.shared.Token;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static java.util.Objects.isNull;

@Component
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private Token tokenService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        var token = getToken(request);
        var isValid = this.tokenService.isValidToken(token);
        if (isValid) authenticateClient(token);
        filterChain.doFilter(request, response);
    }


    private void authenticateClient(String token) {
        var id = tokenService.getUserUuid(token);
        var user = userRepository.findByUuid(id);
        if(isNull(user)) throw new RuntimeException("User not found");
        var authentication = new UsernamePasswordAuthenticationToken(user, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String getToken(HttpServletRequest request) {
        var token = request.getHeader("Authorization");
        if (isNull(token) || !token.startsWith("Bearer ")) return null;
        int length = token.length();
        return token.substring(7, length);
    }
}
