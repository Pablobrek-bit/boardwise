package com.henrique.pablo.BoardWise.infrastructure.persistence.converter;

import com.henrique.pablo.BoardWise.domain.model.ProjectModel;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.Project;

public class ProjectConverter {

    public static ProjectModel toDomain(Project entity){
        if(entity == null) return null;

        return ProjectModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .owner(UserConverter.toDomain(entity.getOwner()))
                .build();
    }

    public static Project toEntity(ProjectModel model){
        if(model == null) return null;

        return Project.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .owner(UserConverter.toEntity(model.getOwner()))
                .build();
    }

    public static Project toEntityWithoutOwner( ProjectModel model){
        if(model == null) return null;

        return Project.builder()
                .name(model.getName())
                .description(model.getDescription())
                .build();
    }
}
