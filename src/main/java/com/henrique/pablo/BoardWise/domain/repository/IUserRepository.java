package com.henrique.pablo.BoardWise.domain.repository;

import com.henrique.pablo.BoardWise.domain.model.UserModel;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.User;

import java.util.Optional;

public interface IUserRepository{
    UserModel save(UserModel user);
    Optional<UserModel> findById(String id);
    Optional<UserModel> findByEmail(String email);
    Optional<User> findByIdWithRoles(String id);
}
