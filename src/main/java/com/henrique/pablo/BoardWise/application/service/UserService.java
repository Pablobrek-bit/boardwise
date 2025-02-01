package com.henrique.pablo.BoardWise.application.service;

import com.henrique.pablo.BoardWise.application.dto.UserRequest;
import com.henrique.pablo.BoardWise.application.dto.UserResponse;
import com.henrique.pablo.BoardWise.domain.repository.IRoleRepository;
import com.henrique.pablo.BoardWise.domain.repository.IUserRepository;
import com.henrique.pablo.BoardWise.infrastructure.persistence.converter.UserConverter;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.Role;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder encoder;

    public UserResponse createUser(UserRequest request){
        userRepository.findByEmail(request.email()).ifPresent(u -> {
            throw new RuntimeException("Email already in use");
        });

        User user = UserConverter.requestToEntity(request);

        Role defaultRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role padrão não encontrada"));

        user.addRole(defaultRole);

        User savedUser = userRepository.save(user);

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
