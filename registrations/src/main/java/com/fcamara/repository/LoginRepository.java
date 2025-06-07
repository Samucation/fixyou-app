package com.fcamara.repository;

import com.fcamara.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LoginRepository extends JpaRepository<Login, UUID> {

    boolean existsByUsername(String username);

}