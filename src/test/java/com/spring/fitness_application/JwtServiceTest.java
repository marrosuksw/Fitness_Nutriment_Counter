package com.spring.fitness_application;

import com.spring.fitness_application.jwt.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;


import java.util.Base64;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class JwtServiceTest {
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        jwtService.setJwtSecret(Base64.getEncoder().encodeToString("01234567890123456789012345678901".getBytes()));
        jwtService.setJwtExpiration(1000L * 30);
    }

    @Test
    void shouldGenerateAndExtractIdFromToken() {
        Long expectedUserId = 123L;
        String token = jwtService.generateAccessToken(expectedUserId);

        Long actualUserId = jwtService.extractId(token);

        assertEquals(expectedUserId, actualUserId);
    }

    @Test
    void shouldValidateValidToken() {
        String token = jwtService.generateAccessToken(999L);
        assertTrue(jwtService.validateToken(token));
        System.out.println(token);
    }

    @Test
    void shouldNotValidateInvalidToken() {
        String invalidToken = "invalid.token.here";
        assertFalse(jwtService.validateToken(invalidToken));
    }
    @Test
    void shouldNotValidateExpiredToken() throws InterruptedException {
        //Given
        JwtService jwtService = new JwtService();
        String shortLivedSecret = Base64.getEncoder().encodeToString("01234567890123456789012345678901".getBytes());
        ReflectionTestUtils.setField(jwtService, "jwtSecret", shortLivedSecret);
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 1000L);
        String token = jwtService.generateAccessToken(1L);

        Thread.sleep(2000);
        //When
        boolean expired = jwtService.validateToken(token);

        //Then
        assertFalse(expired, "Expired token");
    }

    // --------------- Cookie test ---------------

    @Test
    void shouldSetAccessAndRefreshTokenCookies() throws InterruptedException {
        //Given
        JwtService jwtService = new JwtService();
        String secret = Base64.getEncoder().encodeToString("01234567890123456789012345678901".getBytes());
        ReflectionTestUtils.setField(jwtService, "jwtSecret", secret);
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 10000L);
        ReflectionTestUtils.setField(jwtService, "jwtRefreshExpiration", 100000L);
        String accessToken = jwtService.generateAccessToken(1L);
        String refreshToken = jwtService.generateRefreshToken(1L);

        HttpServletResponse mockResponse = Mockito.mock(HttpServletResponse.class);

        //When
        jwtService.setTokenCookies(mockResponse, accessToken, refreshToken);

        //Then
        ArgumentCaptor<Cookie> cookieCaptor = ArgumentCaptor.forClass(Cookie.class);
        verify(mockResponse, atLeastOnce()).addCookie(cookieCaptor.capture());

        boolean accessTokenFound = false;
        boolean refreshTokenFound = false;

        for (Cookie cookie : cookieCaptor.getAllValues()) {
            if ("accessToken".equals(cookie.getName())) {
                accessTokenFound = true;
                assertTrue(cookie.isHttpOnly());
                assertEquals("/", cookie.getPath());
                assertNotNull(cookie.getValue());
            }
            if ("refreshToken".equals(cookie.getName())) {
                refreshTokenFound = true;
                assertTrue(cookie.isHttpOnly());
                assertEquals("/", cookie.getPath());
                assertNotNull(cookie.getValue());
            }
        }

        assertTrue(accessTokenFound, "access token cookie should be set");
        assertTrue(refreshTokenFound, "refresh token cookie should be set");

    }
//    @Test
//    void shouldExtractAccessTokenFromRequestCookie() {
//        // Given
//        JwtService jwtService = new JwtService();
//        String expectedToken = "mocked.jwt.token";
//        Cookie accessTokenCookie = new Cookie("accessToken", expectedToken);
//        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
//
//        when(mockRequest.getCookies()).thenReturn(new Cookie[]{accessTokenCookie});
//
//        // When
//        String extractedToken = jwtService.extractTokenFromCookie(mockRequest);
//
//        // Then
//        assertEquals(expectedToken, extractedToken);
//    }
}
