package com.henrique.pablo.BoardWise.infrastructure.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.henrique.pablo.BoardWise.domain.repository.IUserRepository;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.User;
import com.henrique.pablo.BoardWise.shared.exception.UserNotFoundException;
import com.henrique.pablo.BoardWise.shared.utils.TokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final TokenUtil tokenUtil;
    private final IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var authorizationHeader = request.getHeader("Authorization");

        var isRouteToCreateUser = request.getRequestURI().contains("/users") && request.getMethod().equals("POST");
        var isRouteToAuthenticate =
                request.getRequestURI().contains("/auth") && request.getMethod().equals("POST");

        if(isRouteToCreateUser || isRouteToAuthenticate){
            filterChain.doFilter(request, response);
            return;
        }

        if(!validateAuthorizationHeader(authorizationHeader)) {
            throw new RuntimeException("Authorization header is invalid");
        }

        String token = getToken(authorizationHeader);

        if(!tokenUtil.tokenIsValid(token)) {
            throw new RuntimeException("Token is invalid");
        }

        DecodedJWT decodedJWT = tokenUtil.decodeToken(token);

        String userId = decodedJWT.getSubject();

        User user = userRepository.findByIdWithRoles(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        setAuthenticationContext(request, user);

        filterChain.doFilter(request, response);
    }

    private boolean validateAuthorizationHeader(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.startsWith("Bearer ")
                && authorizationHeader.split("\\.").length == 3; // Exemplo básico de JWT bem formatado
    }


    private String getToken(String authorizationHeader) {
        if (authorizationHeader == null || authorizationHeader.length() < 8) {
            throw new IllegalArgumentException("Invalid Authorization header format");
        }
        return authorizationHeader.substring(7);
    }


    private void setAuthenticationContext(HttpServletRequest request, User user){
        request.setAttribute("id", user.getId());
        var authorization = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authorization);
    }
}
