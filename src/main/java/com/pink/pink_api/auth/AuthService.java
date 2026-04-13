package com.pink.pink_api.auth;

import com.pink.pink_api.auth.dtos.LoginRequest;
import com.pink.pink_api.auth.dtos.LoginResponse;
import com.pink.pink_api.auth.jwt.Jwt;
import com.pink.pink_api.auth.jwt.JwtService;
import com.pink.pink_api.users.UserNotFoundException;
import com.pink.pink_api.users.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginResponse login(@Valid LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken( request.getUsername(), request.getPassword())
        );

        var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        if(!user.isActive())
            throw new BadCredentialsException("Inactive user");

        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        saveRefreshToken(user.getId(), refreshToken.toString());

        return new LoginResponse(accessToken, refreshToken);
    }

    private void saveRefreshToken(Integer userId, String token) {
        var refToken = new RefreshToken();
        refToken.setUserId(userId);
        refToken.setToken(token);
        tokenRepository.save(refToken);
    }

    public Jwt refreshAccessToken(String refreshToken) {
        if(refreshToken == null)
            throw new BadCredentialsException("Invalid refresh token");

        Jwt jwt = jwtService.parseToken(refreshToken);

        if(jwt == null || jwt.isExpired())
            throw new BadCredentialsException("Invalid refresh token");

        if(!tokenRepository.existsByToken(refreshToken))
            throw new BadCredentialsException("Invalid refresh token");

        var user = userRepository
                .findById(jwt.getUserId())
                .orElseThrow(UserNotFoundException::new);
        if(!user.isActive())
            throw new BadCredentialsException("Not authorized to refresh token");
        return jwtService.generateAccessToken(user);
    }

    public void logout(String refreshToken) {
        if(refreshToken == null)
            return;

        Jwt jwt = jwtService.parseToken(refreshToken);
        var userId = jwt.getUserId();
        var tokenIds = tokenRepository.findByUserId(userId).stream().map(t-> t.getId()).toList();
        tokenRepository.deleteAllById(tokenIds);
    }
}
