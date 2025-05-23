package com.spring.fitness_application.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService
{
    @Setter
    @Value("${jwt.expiration}")
    private Long jwtExpiration;
    @Value("${jwt.refresh-expiration}")
    private Long jwtRefreshExpiration;
    @Setter
    @Value("${jwt.secret}")
    private String jwtSecret;

    public JwtService(){}

    private SecretKey getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(Long id) {
        long now = System.currentTimeMillis();
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims(claims)
                .issuedAt(new Date(now))
                .expiration(new Date(now + jwtExpiration))
                .subject(id.toString())
                .signWith(getKey())
                .compact();
    }
    public String generateRefreshToken(Long id) {
        long now = System.currentTimeMillis();
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims(claims)
                .issuedAt(new Date(now))
                .expiration(new Date(now + jwtRefreshExpiration))
                .subject(id.toString())
                .signWith(getKey())
                .compact();
    }
    public Long extractId(String token) {
            String subject =  Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
            return Long.parseLong(subject);
    }
    public boolean validateToken(String token) {
            try{
                Jwts.parser()
                        .verifyWith(getKey())
                        .build()
                        .parseSignedClaims(token);
                return true;
            }
            catch (Exception e){
            return false;
            }
    }
    public void setTokenCookies(HttpServletResponse response, String accessToken, String refreshToken) {
        Cookie accessCookie = new Cookie("accessToken", accessToken);
        accessCookie.setHttpOnly(true);
        accessCookie.setSecure(true);
        accessCookie.setPath("/");
        accessCookie.setMaxAge(Math.toIntExact(jwtExpiration.intValue() / 1000));

        Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(true);
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(Math.toIntExact(jwtRefreshExpiration.intValue() / 1000));

        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);
    }
    public String extractTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("accessToken")){
                    return cookie.getValue();
                }
            }
        }
        //implement logging for more information if cookies fail
        System.out.println("Cookie not found");
        return null;
    }
}
