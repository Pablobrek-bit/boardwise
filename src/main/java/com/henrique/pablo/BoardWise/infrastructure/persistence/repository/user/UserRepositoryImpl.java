package com.henrique.pablo.BoardWise.infrastructure.persistence.repository.user;

import com.henrique.pablo.BoardWise.domain.model.UserModel;
import com.henrique.pablo.BoardWise.domain.repository.IRoleRepository;
import com.henrique.pablo.BoardWise.domain.repository.IUserRepository;
import com.henrique.pablo.BoardWise.infrastructure.persistence.converter.RoleConverter;
import com.henrique.pablo.BoardWise.infrastructure.persistence.converter.UserConverter;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.Role;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements IUserRepository {

    private final UserJpaRepository userJpaRepository;
    private final IRoleRepository roleRepository;


    @Override
    @Transactional
    public UserModel save(UserModel user) {
//        User userEntity = UserConverter.toEntity(user);
//

//
//        var userCreated = userJpaRepository.save(userEntity);
//
//        userCreated.addRole(defaultRole);
//
//        return UserConverter.toDomain(userJpaRepository.save(userCreated));

        User userEntity = UserConverter.toEntity(user);

        Role defaultRole = RoleConverter.toEntity(roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role not found")));

        userEntity.addRole(defaultRole);

        return UserConverter.toDomain(userJpaRepository.save(userEntity));
    }

    @Override
    public Optional<UserModel> findById(String id) {
        return userJpaRepository.findById(id)
                .map(UserConverter::toDomain);
    }

    @Override
    public Optional<UserModel> findByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .map(UserConverter::toDomain);
    }

    @Override
    public Optional<User> findByEmailWithRoles(String email) {
        return userJpaRepository.findByEmailWithRoles(email);
    }

    @Override
    public Optional<User> findByIdWithRoles(String id) {
        return userJpaRepository.findByIdWithRoles(id);
    }

}
