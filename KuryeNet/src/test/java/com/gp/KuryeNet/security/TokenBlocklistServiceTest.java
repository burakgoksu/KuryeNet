package com.gp.KuryeNet.security;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import com.gp.KuryeNet.core.security.TokenBlocklistService;

class TokenBlocklistServiceTest {

    @Test
    void shouldRevokeTokenIdForTtl() {
        TokenBlocklistService service = new TokenBlocklistService();
        String tokenId = "token-id";

        assertFalse(service.isRevoked(tokenId));
        service.revoke(tokenId, Duration.ofMinutes(5));
        assertTrue(service.isRevoked(tokenId));
    }
}
