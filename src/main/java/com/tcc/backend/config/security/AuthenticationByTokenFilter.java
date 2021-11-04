package com.tcc.backend.config.security;

import com.tcc.backend.models.User;
import com.tcc.backend.resources.UserRepository;
import com.tcc.backend.services.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationByTokenFilter extends OncePerRequestFilter {
    private TokenService tokenService;
    private UserRepository userRepository;

    public AuthenticationByTokenFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenByRequest(request);
        boolean isTokenValid = tokenService.isValid(token);

        if (isTokenValid) {
            authUser(token);
        }

        filterChain.doFilter(request, response);
    }

    private void authUser(String token) {
        Integer userId = tokenService.getIdUserByToken(token);
        User user = userRepository.findById(userId).get();

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String getTokenByRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token != null && !token.isEmpty() && token.startsWith("Bearer ")) {
            return token.substring(7, token.length());
        }

        return null;
    }
}
