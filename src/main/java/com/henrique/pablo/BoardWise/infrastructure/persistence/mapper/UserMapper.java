package com.henrique.pablo.BoardWise.infrastructure.persistence.mapper;

import com.henrique.pablo.BoardWise.domain.model.UserModel;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserModel toDomain(User entity);
    User toEntity(UserModel domain);
}
