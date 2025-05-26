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

    @Query("SELECT u FROM User u " +
            "JOIN FETCH u.profile " +
            "LEFT JOIN FETCH u.roles " +
            "WHERE u.username = :username")
    Optional<User> findByUsername(@Param("username") String username);

    @Query("SELECT u FROM User u " +
            "JOIN u.roles r " +
            "WHERE r.name = :role")
    List<User> findByRole(@Param("role") UserRole role);

    List<User> findAllByOrderByUsernameAsc();

    Long countByProfile(Profile profile);

    List<User> findByProfile(Profile profile);

    List<User> findByProfile_Unit(String unit);

    List<User> findByProfile_Department(String department);

}
