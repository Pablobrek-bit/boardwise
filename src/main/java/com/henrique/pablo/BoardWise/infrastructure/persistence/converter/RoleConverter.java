package com.henrique.pablo.BoardWise.infrastructure.persistence.converter;

import com.henrique.pablo.BoardWise.domain.model.RoleModel;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.Role;

import java.util.stream.Collectors;

public class RoleConverter {

    public static RoleModel toDomain(Role entity) {
        return RoleModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .users(entity.getUsers().stream()
                        .map(UserConverter::toDomain)
                        .collect(Collectors.toSet()))
                .build();
    }

    public static Role toEntity(RoleModel domain) {
        return Role.builder()
                .id(domain.getId())
                .name(domain.getName())
                .createdAt(domain.getCreatedAt())
                .users(domain.getUsers().stream()
                        .map(UserConverter::toEntity)
                        .collect(Collectors.toSet()))
                .build();
    }

}
