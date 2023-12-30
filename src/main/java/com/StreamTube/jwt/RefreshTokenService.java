package com.StreamTube.jwt;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.StreamTube.models.RefreshToken;
import com.StreamTube.repositories.RefreshTokenRepository;
import com.StreamTube.repositories.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class RefreshTokenService {

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;
	@Autowired
	private UserRepository userInfoRepository;

	private static final String SECRET_KEY = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

	// Tạo RefreshToken để duy trì đăng nhập
	    public RefreshToken createRefreshToken(String username) {
	        String refreshToken = Jwts.builder()
	            .setSubject(username)
	            .setIssuedAt(new Date())
	            .setExpiration(new Date(System.currentTimeMillis() + Duration.ofDays(10).toMillis())) // 10 days
	            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
	            .compact();

	        RefreshToken token = RefreshToken.builder()
	            .user(userInfoRepository.findByUsername(username).get())
	            .refreshToken(refreshToken)
	            .expiryDate(Instant.now().plus(Duration.ofDays(10)))
	            .build();

	        return refreshTokenRepository.save(token);
	    }
	
	
	public Optional<RefreshToken> findByToken(String token) {
		return refreshTokenRepository.findByToken(token);
	}
	
	public RefreshToken verifyExpiration(RefreshToken token) {
		if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
			refreshTokenRepository.delete(token);
			throw new RuntimeException(
					token.getRefreshToken() + " Refresh token was expired. Please make a new signin request");
		}
		return token;
	}

}