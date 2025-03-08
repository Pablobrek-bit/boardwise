package com.henrique.pablo.BoardWise.domain.repository;

import com.henrique.pablo.BoardWise.domain.model.RoleModel;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface IRoleRepository {
    Optional<RoleModel> findByName(String name);
    Optional<RoleModel> findById(Integer id);
}
