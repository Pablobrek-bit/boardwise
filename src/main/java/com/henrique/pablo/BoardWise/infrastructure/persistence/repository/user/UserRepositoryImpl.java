package com.henrique.pablo.BoardWise.infrastructure.persistence.repository.user;

import com.henrique.pablo.BoardWise.domain.model.UserModel;
import com.henrique.pablo.BoardWise.domain.repository.IUserRepository;
import com.henrique.pablo.BoardWise.infrastructure.persistence.converter.UserConverter;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements IUserRepository {

    private final UserJpaRepository userJpaRepository;


    @Override
    public User save(User user) {
        return userJpaRepository.save(user);
    }

    @Override
    public Optional<UserModel> findById(String id) {
        return userJpaRepository.findById(id)
                .map(UserConverter::toDomain);
    }

    @Override
    public Optional<UserModel> findByEmail(String email) {
        return Optional.ofNullable(userJpaRepository.findByEmail(email))
                .map(UserConverter::toDomain);
    }

}
