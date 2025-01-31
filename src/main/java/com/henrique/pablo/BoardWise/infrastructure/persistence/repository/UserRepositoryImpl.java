package com.henrique.pablo.BoardWise.infrastructure.persistence.repository;

import com.henrique.pablo.BoardWise.domain.model.UserModel;
import com.henrique.pablo.BoardWise.domain.repository.IUserRepository;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.User;
import com.henrique.pablo.BoardWise.infrastructure.persistence.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements IUserRepository {

    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    @Override
    public UserModel save(UserModel user) {
        User userEntity = userMapper.toEntity(user);
        System.out.println("UserEntity: " + userEntity);
        userEntity = userJpaRepository.save(userEntity);
        return userMapper.toDomain(userEntity);
    }

    @Override
    public Optional<UserModel> findById(String id) {
        return userJpaRepository.findById(id)
                .map(userMapper::toDomain);
    }
}
