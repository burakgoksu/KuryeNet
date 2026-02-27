package com.gp.KuryeNet.security;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.gp.KuryeNet.core.security.LoginAttemptService;

@SpringBootTest
@TestPropertySource(properties = {
        "security.login.max-attempts=2",
        "security.login.lock-minutes=1"
})
class LoginAttemptServiceTest {

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Test
    void shouldLockAfterMaxAttempts() {
        String key = "user@example.com|127.0.0.1";

        assertFalse(loginAttemptService.isBlocked(key));
        loginAttemptService.loginFailed(key);
        assertFalse(loginAttemptService.isBlocked(key));
        loginAttemptService.loginFailed(key);
        assertTrue(loginAttemptService.isBlocked(key));
    }
}
