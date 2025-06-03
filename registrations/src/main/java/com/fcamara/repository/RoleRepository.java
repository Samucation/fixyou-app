package com.fcamara.repository;

import com.fcamara.model.Role;
import com.fcamara.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(UserRole name);
}
