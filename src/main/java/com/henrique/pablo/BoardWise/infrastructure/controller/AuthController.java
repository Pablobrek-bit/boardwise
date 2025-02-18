package com.henrique.pablo.BoardWise.infrastructure.controller;

import com.henrique.pablo.BoardWise.application.dto.auth.JwtResponse;
import com.henrique.pablo.BoardWise.application.dto.auth.LoginRequest;
import com.henrique.pablo.BoardWise.application.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest){
        JwtResponse jwtResponse = authService.auth(loginRequest);

        return ResponseEntity.ok(jwtResponse);
    }

}
