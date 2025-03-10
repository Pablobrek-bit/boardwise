package com.henrique.pablo.BoardWise.infrastructure.persistence.repository.user;

import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.Role;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.User;
import com.henrique.pablo.BoardWise.infrastructure.persistence.repository.role.RoleJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Set;

@DataJpaTest
class UserJpaRepositoryTest {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private RoleJpaRepository roleJpaRepository;

    private User user;

    @BeforeEach
    void beforeEach(){
        Role role = roleJpaRepository.save(Role.builder().name("ROLE_USER").createdAt(LocalDateTime.now()).build());

        this.user = User.builder()
                .username("test")
                .passwordHash("test")
                .email("test@gmail.com")
                .roles(Set.of(role))
                .build();

        userJpaRepository.save(user);
    }

    @Test
    void save_MustBeAbleToSaveAUser() {
        User user = User.builder()
                .username("test1")
                .passwordHash("test1")
                .email("test1@gmail.com")
                .build();

        User savedUser = userJpaRepository.save(user);

        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals("test1", savedUser.getUsername());
    }

    @Test
    void findById_MustBeAbleToFindAUserById() {
        User user = userJpaRepository.findById(this.user.getId()).orElse(null);

        Assertions.assertNotNull(user);
        Assertions.assertEquals("test", user.getUsername());
    }

    @Test
    void findByIdWithRoles_MustBeAbleToFindAUserByIdWithRoles() {
        User user = userJpaRepository.findByIdWithRoles(this.user.getId()).orElse(null);

        Assertions.assertNotNull(user);
        Assertions.assertEquals("test", user.getUsername());
        Assertions.assertEquals(1, user.getRoles().size());
        Assertions.assertEquals("ROLE_USER", user.getRoles().iterator().next().getName());
    }

}