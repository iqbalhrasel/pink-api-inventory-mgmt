package com.pink.pink_api.auth;

import com.pink.pink_api.auth.dtos.LoginRequest;
import com.pink.pink_api.auth.dtos.LoginResponse;
import com.pink.pink_api.auth.jwt.Jwt;
import com.pink.pink_api.auth.jwt.JwtConfig;
import com.pink.pink_api.auth.jwt.JwtResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtConfig jwtConfig;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request, HttpServletResponse response){

        LoginResponse loginResponse = authService.login(request);

        var refreshToken = loginResponse.getRefreshToken().toString();

        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(false) // false for localhost, true for production HTTPS
                .path("/api/auth")
                .maxAge(jwtConfig.getRefreshTokenExpiration())
                .sameSite("None") //None for dev, Strict for prod
                .build();
        //must use withCredentials: true in axios to let browser receive+store the token

        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok(new JwtResponse(loginResponse.getAccessToken().toString()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(@CookieValue(value = "refreshToken") String refreshToken){
        Jwt accessToken = authService.refreshAccessToken(refreshToken);

        return ResponseEntity.ok(new JwtResponse(accessToken.toString()));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue(value = "refreshToken", required = false) String refreshToken,
                                    HttpServletResponse response){
        authService.logout(refreshToken);

        SecurityContextHolder.clearContext();
        ResponseCookie cookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(false) // false for localhost, true for production HTTPS
                .path("/auth")
                .maxAge(0)
                .sameSite("Lax") //None for dev, Strict for prod
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentials(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Bad credentials");
    }
}
