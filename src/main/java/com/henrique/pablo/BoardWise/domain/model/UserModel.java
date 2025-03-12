package com.henrique.pablo.BoardWise.domain.model;

import com.henrique.pablo.BoardWise.application.dto.user.UserUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModel {

    private String id;
    private String username;
    private String passwordHash;
    private String email;
    private LocalDateTime createdAt;
    private Set<RoleModel> roles = new HashSet<>();
    private Set<ProjectModel> projects = new HashSet<>();
    private Set<ProjectModel> participatingProjects = new HashSet<>();
    private Set<CardModel> cards = new HashSet<>();
    private Set<CommentModel> comments = new HashSet<>();

    public void assignDefaultRole(RoleModel role){
        roles = new HashSet<>();
        roles.add(role);
    }

    public void updateFields(UserUpdateRequest request, PasswordEncoder encoder){
        Optional.ofNullable(request.username()).ifPresent(this::setUsername);
        Optional.ofNullable(request.password()).ifPresent(pwd ->
                this.setPasswordHash(encoder.encode(pwd))
        );
        Optional.ofNullable(request.email()).ifPresent(this::setEmail);
    }

}
