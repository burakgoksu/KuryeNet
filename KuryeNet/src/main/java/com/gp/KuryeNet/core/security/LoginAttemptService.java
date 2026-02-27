package com.gp.KuryeNet.core.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.gp.KuryeNet.core.config.properties.SecurityProperties;

@Service
public class LoginAttemptService {

    private static class Attempt {
        private int count;
        private Instant lockedUntil;
    }

    private final Map<String, Attempt> attempts = new ConcurrentHashMap<>();

    private final SecurityProperties securityProperties;

    public LoginAttemptService(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    public boolean isBlocked(String key) {
        Attempt attempt = attempts.get(key);
        if (attempt == null) {
            return false;
        }
        if (attempt.lockedUntil == null) {
            return false;
        }
        if (attempt.lockedUntil.isBefore(Instant.now())) {
            attempts.remove(key);
            return false;
        }
        return true;
    }

    public void loginSucceeded(String key) {
        attempts.remove(key);
    }

    public void loginFailed(String key) {
        Attempt attempt = attempts.computeIfAbsent(key, ignored -> new Attempt());
        attempt.count++;
        if (attempt.count >= securityProperties.getLogin().getMaxAttempts()) {
            attempt.lockedUntil = Instant.now()
                    .plus(securityProperties.getLogin().getLockMinutes(), ChronoUnit.MINUTES);
        }
    }
}
