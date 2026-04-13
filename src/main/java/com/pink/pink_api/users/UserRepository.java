package com.pink.pink_api.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);

    @Query("SELECT u.username FROM User u WHERE u.id=:userId")
    Optional<String> getUsernameBy(@Param(value = "userId") Integer userId);
}
