package com.henrique.pablo.BoardWise.infrastructure.persistence.repository.role;

import com.henrique.pablo.BoardWise.domain.repository.IRoleRepository;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements IRoleRepository {

    private final RoleJpaRepository roleJpaRepository;

    @Override
    public Optional<Role> findByName(String name) {
        return roleJpaRepository.findByName(name);
    }

    @Override
    public Optional<Role> findById(Integer id) {
        return roleJpaRepository.findById(id);
    }
}
