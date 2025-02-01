package com.henrique.pablo.BoardWise.domain.repository;

import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.Role;

import java.util.Optional;

public interface IRoleRepository {
    Optional<Role> findByName(String name);
    Optional<Role> findById(Integer id);
}
