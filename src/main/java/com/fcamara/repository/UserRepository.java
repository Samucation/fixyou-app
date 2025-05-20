package com.fcamara.repository;

import com.fcamara.model.Profile;
import com.fcamara.model.User;
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
           "WHERE u.username = :username")
    Optional<User> findByUsername(@Param("username") String username);

    @Query("SELECT u FROM User u " +
           "JOIN u.profile p " +
           "WHERE p.type = :type ")
    List<User> findByProfileType(@Param("type") String type);

    @Query("SELECT u FROM User u " +
           "JOIN u.profile p " +
           "ORDER BY u.username")
    List<User> findAllOrderedByUsername();


    @Query("SELECT COUNT(u) FROM User u " +
           "JOIN u.profile p " +
           "WHERE p = :profile")
    Long countByProfile(@Param("profile") Profile profile);

}

