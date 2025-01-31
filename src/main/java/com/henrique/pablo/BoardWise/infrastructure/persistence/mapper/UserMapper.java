package com.henrique.pablo.BoardWise.infrastructure.persistence.mapper;

import com.henrique.pablo.BoardWise.domain.model.UserModel;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "passwordHash", source = "passwordHash")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "roles", ignore = true)
    UserModel toDomain(User entity);

    @Mapping(target = "passwordHash", source = "passwordHash")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "roles", ignore = true)
    User toEntity(UserModel domain);
}
