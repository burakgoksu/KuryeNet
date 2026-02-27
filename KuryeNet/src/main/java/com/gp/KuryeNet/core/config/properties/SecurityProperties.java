package com.gp.KuryeNet.core.config.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@Validated
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {

    @Valid
    private final Jwt jwt = new Jwt();

    @Valid
    private final Login login = new Login();

    @Valid
    private final Cors cors = new Cors();

    public Jwt getJwt() {
        return jwt;
    }

    public Login getLogin() {
        return login;
    }

    public Cors getCors() {
        return cors;
    }

    public static class Jwt {
        @NotBlank
        private String secret = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

        @NotBlank
        private String issuer = "kurye-net";

        @NotBlank
        private String audience = "kurye-net-api";

        @Min(1)
        private long accessTokenMinutes = 1440;

        @Min(1)
        private long refreshTokenDays = 7;

        @Min(0)
        private long clockSkewSeconds = 30;

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public String getIssuer() {
            return issuer;
        }

        public void setIssuer(String issuer) {
            this.issuer = issuer;
        }

        public String getAudience() {
            return audience;
        }

        public void setAudience(String audience) {
            this.audience = audience;
        }

        public long getAccessTokenMinutes() {
            return accessTokenMinutes;
        }

        public void setAccessTokenMinutes(long accessTokenMinutes) {
            this.accessTokenMinutes = accessTokenMinutes;
        }

        public long getRefreshTokenDays() {
            return refreshTokenDays;
        }

        public void setRefreshTokenDays(long refreshTokenDays) {
            this.refreshTokenDays = refreshTokenDays;
        }

        public long getClockSkewSeconds() {
            return clockSkewSeconds;
        }

        public void setClockSkewSeconds(long clockSkewSeconds) {
            this.clockSkewSeconds = clockSkewSeconds;
        }
    }

    public static class Login {
        @Min(1)
        private int maxAttempts = 5;

        @Min(1)
        private long lockMinutes = 15;

        public int getMaxAttempts() {
            return maxAttempts;
        }

        public void setMaxAttempts(int maxAttempts) {
            this.maxAttempts = maxAttempts;
        }

        public long getLockMinutes() {
            return lockMinutes;
        }

        public void setLockMinutes(long lockMinutes) {
            this.lockMinutes = lockMinutes;
        }
    }

    public static class Cors {
        @NotEmpty
        private List<String> allowedOrigins = List.of("http://localhost:8081");

        public List<String> getAllowedOrigins() {
            return allowedOrigins;
        }

        public void setAllowedOrigins(List<String> allowedOrigins) {
            this.allowedOrigins = allowedOrigins;
        }
    }
}
