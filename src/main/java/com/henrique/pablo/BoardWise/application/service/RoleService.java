package com.henrique.pablo.BoardWise.application.service;

import com.henrique.pablo.BoardWise.domain.model.RoleModel;
import com.henrique.pablo.BoardWise.domain.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final IRoleRepository roleRepository;

    public Optional<RoleModel> findByName(String roleUser) {
        return roleRepository.findByName(roleUser);
    }
}
