package com.henrique.pablo.BoardWise.infrastructure.persistence.repository;

import com.henrique.pablo.BoardWise.domain.model.UserModel;
import com.henrique.pablo.BoardWise.domain.repository.IUserRepository;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.User;
import com.henrique.pablo.BoardWise.infrastructure.persistence.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements IUserRepository {

    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;

    @Override
    public UserModel save(UserModel user) {
        var passwordEncoder = encoder.encode(user.getPasswordHash());
        user.setPasswordHash(passwordEncoder);
        User userEntity = userMapper.toEntity(user);
        userEntity = userJpaRepository.save(userEntity);
        return userMapper.toDomain(userEntity);
    }

    @Override
    public Optional<UserModel> findById(String id) {
        return userJpaRepository.findById(id)
                .map(userMapper::toDomain);
    }

    @Override
    public Optional<UserModel> findByEmail(String email) {
        return Optional.ofNullable(userJpaRepository.findByEmail(email))
                .map(userMapper::toDomain);
    }

}
