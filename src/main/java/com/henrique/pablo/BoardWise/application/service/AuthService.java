package com.henrique.pablo.BoardWise.application.service;

import com.henrique.pablo.BoardWise.application.dto.JwtResponse;
import com.henrique.pablo.BoardWise.application.dto.LoginRequest;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.User;
import com.henrique.pablo.BoardWise.shared.utils.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    public JwtResponse auth(LoginRequest loginRequest){
        String token = "";

        var tokenUsername = new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());

        var authentication = authenticationManager.authenticate(tokenUsername);

        if(authentication.isAuthenticated()){
            token = TokenUtil.generateToken((User) authentication.getPrincipal());
        }

        return new JwtResponse(token);
    }

}
