package com.fcamara.repository;

import com.fcamara.model.Profile;
import com.fcamara.model.User;
import com.fcamara.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 🔍 Busca por username
    @Query("SELECT u FROM User u " +
            "JOIN FETCH u.profile " +
            "LEFT JOIN FETCH u.roles " +
            "WHERE u.username = :username")
    Optional<User> findByUsername(@Param("username") String username);

    // 🔍 Busca usuários por Role
    @Query("SELECT u FROM User u " +
            "JOIN u.roles r " +
            "WHERE r.name = :role")
    List<User> findByRole(@Param("role") UserRole role);

    // 🔍 Lista todos ordenados por username
    List<User> findAllByOrderByUsernameAsc();

    // 🔍 Contagem por profile
    Long countByProfile(Profile profile);

    // 🔍 Busca usuários por Profile
    List<User> findByProfile(Profile profile);

    // 🔍 Busca por Branch (Substitui antigo findByProfile_Unit)
    List<User> findByProfile_Branch_Id(Long branchId);

    // 🔍 Busca por Departamento (como é ManyToMany precisa de Query)
    @Query("SELECT u FROM User u " +
            "JOIN u.profile.departments d " +
            "WHERE d.name = :departmentName")
    List<User> findByDepartmentName(@Param("departmentName") String departmentName);

    @Query("SELECT u FROM User u " +
            "JOIN u.profile.departments d " +
            "WHERE d.id = :departmentId")
    List<User> findByDepartmentId(@Param("departmentId") Long departmentId);

    // 🔍 Busca por Keycloak ID
    @Query("SELECT u FROM User u " +
            "JOIN FETCH u.profile " +
            "LEFT JOIN FETCH u.roles " +
            "WHERE u.keycloakId = :keycloakId")
    Optional<User> findByKeycloakId(@Param("keycloakId") String keycloakId);

}
