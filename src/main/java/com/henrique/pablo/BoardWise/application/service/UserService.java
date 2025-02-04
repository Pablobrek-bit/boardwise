package com.henrique.pablo.BoardWise.application.service;

import com.henrique.pablo.BoardWise.application.dto.UserRequest;
import com.henrique.pablo.BoardWise.application.dto.UserResponse;
import com.henrique.pablo.BoardWise.domain.model.RoleModel;
import com.henrique.pablo.BoardWise.domain.model.UserModel;
import com.henrique.pablo.BoardWise.domain.repository.IRoleRepository;
import com.henrique.pablo.BoardWise.domain.repository.IUserRepository;
import com.henrique.pablo.BoardWise.shared.exception.EmailAlreadyExistsException;
import com.henrique.pablo.BoardWise.shared.exception.RoleNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder encoder;

    @Transactional
    public UserResponse createUser(UserRequest request){
        userRepository.findByEmail(request.email()).ifPresent(u -> {
            throw new EmailAlreadyExistsException("Email already in use");
        });

        UserModel user = UserModel.builder()
                .username(request.username())
                .email(request.email())
                .passwordHash(encoder.encode(request.password()))
                .createdAt(LocalDateTime.now())
                .build();

        RoleModel defaultRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));

        user.addRole(defaultRole);

        UserModel savedUser = userRepository.save(user);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getCreatedAt()
        );
    }

    public UserResponse getUser(String id){
        return userRepository.findById(id)
                .map(user -> new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getCreatedAt()))
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
