package com.security.demo.todos.service;

import com.security.demo.todos.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailsRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByUsername(String username);
}
