package com.StreamTube.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.StreamTube.models.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
	Optional<RefreshToken> findByToken(String token);

	Optional<RefreshToken> findByUserId(Integer userId);

}