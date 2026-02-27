package com.gp.KuryeNet.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.gp.KuryeNet.core.utulities.jwt.JwtTokenType;
import com.gp.KuryeNet.core.utulities.jwt.JwtUtil;

@SpringBootTest
class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    void shouldGenerateAndValidateAccessToken() {
        UserDetails user = new User("test@example.com", "pass", Collections.emptyList());
        String token = jwtUtil.generateAccessToken(user);

        assertNotNull(token);
        assertTrue(jwtUtil.validateToken(token, user, JwtTokenType.ACCESS));
    }

    @Test
    void shouldGenerateAndValidateRefreshToken() {
        UserDetails user = new User("test@example.com", "pass", Collections.emptyList());
        String token = jwtUtil.generateRefreshToken(user.getUsername());

        assertNotNull(token);
        assertTrue(jwtUtil.validateToken(token, user, JwtTokenType.REFRESH));
        String type = jwtUtil.extractClaim(token, claims -> String.valueOf(claims.get("typ")));
        assertEquals("refresh", type);
    }
}
