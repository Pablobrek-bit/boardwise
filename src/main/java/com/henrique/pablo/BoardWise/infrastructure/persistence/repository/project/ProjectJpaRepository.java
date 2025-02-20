package com.henrique.pablo.BoardWise.infrastructure.persistence.repository.project;

import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectJpaRepository extends JpaRepository<Project, String> {
    @Query("SELECT p FROM Project p WHERE p.owner.id = :ownerId AND (LOWER(p.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<Project> findByOwnerIdAndSearchTerm(String ownerId, String searchTerm, Pageable pageable);

    @Query("SELECT p FROM Project p JOIN FETCH p.owner WHERE p.id = :projectId")
    Optional<Project> findByIdWithParticipants(String projectId);

    //query para poder pegar apenas os participantes de um projeto
    @Query("SELECT p FROM Project p JOIN FETCH p.participants WHERE p.id = :projectId")
    Optional<Project> findByIdWithParticipantsOnly(String projectId);
}
