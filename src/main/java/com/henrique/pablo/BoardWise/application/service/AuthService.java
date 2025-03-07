package com.henrique.pablo.BoardWise.application.service;

import com.henrique.pablo.BoardWise.application.dto.auth.JwtResponse;
import com.henrique.pablo.BoardWise.application.dto.auth.LoginRequest;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.User;
import com.henrique.pablo.BoardWise.shared.utils.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    public JwtResponse auth(LoginRequest loginRequest){
        String token = "";

        UsernamePasswordAuthenticationToken tokenUsername = new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());

        Authentication authentication = authenticationManager.authenticate(tokenUsername);

        if(authentication.isAuthenticated()){
            token = TokenUtil.generateToken((User) authentication.getPrincipal());
        }

        return new JwtResponse(token);
    }

}
