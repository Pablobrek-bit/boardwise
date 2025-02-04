package com.henrique.pablo.BoardWise.infrastructure.persistence.converter;

import com.henrique.pablo.BoardWise.application.dto.UserRequest;
import com.henrique.pablo.BoardWise.domain.model.RoleModel;
import com.henrique.pablo.BoardWise.domain.model.UserModel;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.Role;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.User;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class UserConverter {

    public static UserModel toDomain(User entity) {
        return UserModel.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .passwordHash(entity.getPasswordHash())
                .email(entity.getEmail())
                .createdAt(entity.getCreatedAt())
                .roles(toRoleModels(entity.getRoles()))
                .build();
    }

    public static User toEntity(UserModel domain) {
        return User.builder()
                .id(domain.getId())
                .username(domain.getUsername())
                .passwordHash(domain.getPasswordHash())
                .email(domain.getEmail())
                .createdAt(domain.getCreatedAt())
                .roles(toRoleEntitiesWithoutUsers(domain.getRoles(), domain))
                .build();
    }

    private static Set<RoleModel> toRoleModels(Set<Role> roles) {
        return roles.stream()
                .map(role -> RoleModel.builder()
                        .id(role.getId())
                        .name(role.getName())
                        .createdAt(role.getCreatedAt())
                        .build())
                .collect(Collectors.toSet());
    }

    private static Set<Role> toRoleEntitiesWithoutUsers(Set<RoleModel> roles, UserModel user) {
        return roles.stream()
                .map(role -> {
                    Role roleEntity = Role.builder()
                            .id(role.getId())
                            .name(role.getName())
                            .createdAt(role.getCreatedAt())
                            .build();

                    roleEntity.getUsers().add(toEntityWithoutRoles(user));
                    return roleEntity;
                }).collect(Collectors.toSet());

    }

    private static User toEntityWithoutRoles(UserModel domain) {
        return User.builder()
                .id(domain.getId())
                .username(domain.getUsername())
                .passwordHash(domain.getPasswordHash())
                .email(domain.getEmail())
                .createdAt(domain.getCreatedAt())
                .build();
    }


    public static User requestToEntity(UserRequest request) {
        return User.builder()
                .username(request.username())
                .passwordHash(request.password())
                .email(request.email())
                .createdAt(LocalDateTime.now())
                .build();

    }
}
