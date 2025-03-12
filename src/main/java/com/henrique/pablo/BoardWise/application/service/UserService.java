package com.henrique.pablo.BoardWise.application.service;

import com.henrique.pablo.BoardWise.application.dto.user.UserRequest;
import com.henrique.pablo.BoardWise.application.dto.user.UserResponse;
import com.henrique.pablo.BoardWise.application.dto.user.UserUpdateRequest;
import com.henrique.pablo.BoardWise.domain.model.RoleModel;
import com.henrique.pablo.BoardWise.domain.model.UserModel;
import com.henrique.pablo.BoardWise.domain.repository.IUserRepository;
import com.henrique.pablo.BoardWise.infrastructure.persistence.converter.UserConverter;
import com.henrique.pablo.BoardWise.shared.exception.EmailAlreadyExistsException;
import com.henrique.pablo.BoardWise.shared.exception.IdNotFoundException;
import com.henrique.pablo.BoardWise.shared.exception.RoleNotFoundException;
import com.henrique.pablo.BoardWise.shared.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final IUserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse createUser(UserRequest request){
        validateEmailUniqueness(request.email());

        UserModel user = createUserModel(request);
        user.assignDefaultRole(roleService.findByName("ROLE_USER")
                .orElseThrow(() -> new RoleNotFoundException("Default role not found")));

        UserModel savedUser = userRepository.save(user);
        return UserConverter.modelToResponse(savedUser);
    }

    public UserResponse getUserById(String id){
        return userRepository.findById(id)
                .map(UserConverter::modelToResponse)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Transactional
    public UserResponse updateUser(String id, UserUpdateRequest request) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if(request.email() != null){
            validateEmailUniqueness(request.email());
        }

        updateUserFields(user, request);
        UserModel updatedUser = userRepository.save(user);

        return UserConverter.modelToResponse(updatedUser);
    }

    private void validateEmailUniqueness(String email) {
        userRepository.findByEmail(email).ifPresent(u -> {
            throw new EmailAlreadyExistsException("Email already in use");
        });
    }

    private UserModel createUserModel(UserRequest request) {
        String encodedPassword = passwordEncoder.encode(request.password());
        return UserConverter.requestToDomain(request, encodedPassword);
    }

    private void updateUserFields(UserModel user, UserUpdateRequest request) {
        Optional.ofNullable(request.username()).ifPresent(user::setUsername);
        Optional.ofNullable(request.password()).ifPresent(pwd ->
                user.setPasswordHash(passwordEncoder.encode(pwd))
        );
        Optional.ofNullable(request.email()).ifPresent(user::setEmail);
    }
}
