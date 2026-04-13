package com.pink.pink_api.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    List<RefreshToken> findByUserId(Integer userId);
    boolean existsByToken(String token);
}
