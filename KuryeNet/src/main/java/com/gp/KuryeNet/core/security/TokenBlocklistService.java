package com.gp.KuryeNet.core.security;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class TokenBlocklistService {

    private final Map<String, Instant> revokedTokens = new ConcurrentHashMap<>();

    public void revoke(String tokenId, Duration ttl) {
        if (tokenId == null || ttl == null || ttl.isNegative() || ttl.isZero()) {
            return;
        }
        revokedTokens.put(tokenId, Instant.now().plus(ttl));
    }

    public boolean isRevoked(String tokenId) {
        if (tokenId == null) {
            return false;
        }
        Instant expiresAt = revokedTokens.get(tokenId);
        if (expiresAt == null) {
            return false;
        }
        if (expiresAt.isBefore(Instant.now())) {
            revokedTokens.remove(tokenId);
            return false;
        }
        return true;
    }
}
