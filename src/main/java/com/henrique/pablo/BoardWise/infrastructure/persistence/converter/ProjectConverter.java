package com.henrique.pablo.BoardWise.infrastructure.persistence.converter;

import com.henrique.pablo.BoardWise.domain.model.ProjectModel;
import com.henrique.pablo.BoardWise.domain.model.RoleModel;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.Project;

import java.util.stream.Collectors;

public class ProjectConverter {

    public static ProjectModel toDomain(Project entity){
        if(entity == null) return null;

        return ProjectModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .deleted(entity.isDeleted())
                .owner(UserConverter.toDomain(entity.getOwner()))
                .participants(entity.getParticipants().stream().map(UserConverter::toDomain).collect(Collectors.toSet()))
                .build();
    }

    public static ProjectModel toDomainWithoutParticipants(Project entity){
        if(entity == null) return null;

        return ProjectModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .deleted(entity.isDeleted())
                .owner(UserConverter.toDomain(entity.getOwner()))
                .build();
    }

    public static Project toEntity(ProjectModel model){
        if(model == null) return null;

        return Project.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .deleted(model.getDeleted())
                .owner(UserConverter.toEntity(model.getOwner()))
                .build();
    }

    public static Project toEntityWithoutOwner(ProjectModel model){
        if(model == null) return null;

        return Project.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .participants(model.getParticipants().stream()
                        .map(participant -> {
                            if (participant.getRoles() == null || participant.getRoles().isEmpty()) {
                                RoleModel defaultRole = RoleModel.builder()
                                        .name("ROLE_USER")
                                        .build();
                                participant.addRole(defaultRole);
                            }
                            return UserConverter.toEntity(participant);
                        })
                        .collect(Collectors.toSet()))
                .build();
    }

    public static Project toEntityWithoutParticipants(ProjectModel model){
        if(model == null) return null;

        return Project.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .build();
    }
}
