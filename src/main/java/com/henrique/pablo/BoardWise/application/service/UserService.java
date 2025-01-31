package com.henrique.pablo.BoardWise.application.service;

import com.henrique.pablo.BoardWise.application.dto.UserRequest;
import com.henrique.pablo.BoardWise.application.dto.UserResponse;
import com.henrique.pablo.BoardWise.domain.model.UserModel;
import com.henrique.pablo.BoardWise.domain.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final IUserRepository userRepository;

    public UserResponse createUser(UserRequest request){
        UserModel user = UserModel.builder()
                .username(request.username())
                .email(request.email())
                .passwordHash(request.password())
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.findByEmail(user.getEmail()).ifPresent(u -> {
            throw new RuntimeException("Email already in use");
        });

        user = userRepository.save(user);

        return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getCreatedAt());
    }

    public UserResponse getUser(String id){
        return userRepository.findById(id)
                .map(user -> new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getCreatedAt()))
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
