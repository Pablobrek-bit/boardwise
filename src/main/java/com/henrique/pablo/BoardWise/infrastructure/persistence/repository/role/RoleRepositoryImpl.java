package com.henrique.pablo.BoardWise.infrastructure.persistence.repository.role;

import com.henrique.pablo.BoardWise.domain.model.RoleModel;
import com.henrique.pablo.BoardWise.domain.repository.IRoleRepository;
import com.henrique.pablo.BoardWise.infrastructure.persistence.converter.RoleConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements IRoleRepository {

    private final RoleJpaRepository roleJpaRepository;

    @Override
    public Optional<RoleModel> findByName(String name) {
        return roleJpaRepository.findByName(name).map(RoleConverter::toDomain);
    }

    @Override
    public Optional<RoleModel> findById(Integer id) {
        return roleJpaRepository.findById(id).map(RoleConverter::toDomain);
    }
}
