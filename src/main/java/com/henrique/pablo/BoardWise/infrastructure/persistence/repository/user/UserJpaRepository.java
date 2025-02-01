package com.henrique.pablo.BoardWise.infrastructure.persistence.repository.user;

import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<User, String> {
    User findByEmail(String email);
}
