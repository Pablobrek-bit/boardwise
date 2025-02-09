package com.henrique.pablo.BoardWise.infrastructure.persistence.repository.project;

import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectJpaRepository extends JpaRepository<Project, String> {

    Page<Project> findByOwnerIdContainingOrNameOrDescription(String ownerId, String name, String description, Pageable pageable);
}
