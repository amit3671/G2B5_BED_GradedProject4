package com.Application.EmpRegistration.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Application.EmpRegistration.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

}
