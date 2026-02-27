package com.gp.KuryeNet.core.utulities.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.gp.KuryeNet.core.config.properties.SecurityProperties;
import com.gp.KuryeNet.core.utulities.Util.Utils;

import io.jsonwebtoken.io.Decoders;
import java.time.Duration;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;

import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private final SecurityProperties securityProperties;

    public JwtUtil(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
    
    public String extractTokenFromRequest(HttpServletRequest request) {
	    String bearerToken = request.getHeader("Authorization");
	    if (Utils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
	        return bearerToken.substring(7);
	    }
	    return null;
	    
	}

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractTokenId(String token) {
        return extractClaim(token, Claims::getId);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .setAllowedClockSkewSeconds(securityProperties.getJwt().getClockSkewSeconds())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return validateToken(token, userDetails, JwtTokenType.ACCESS);
    }

    public Boolean validateToken(String token, UserDetails userDetails, JwtTokenType expectedType) {
        final String username = extractUsername(token);
        if (!username.equals(userDetails.getUsername()) || isTokenExpired(token)) {
            return false;
        }
        Claims claims = extractAllClaims(token);
        if (!isIssuerValid(claims) || !isAudienceValid(claims)) {
            return false;
        }
        if (expectedType != null && !isTokenTypeValid(claims, expectedType)) {
            return false;
        }
        return true;
    }

    public String generateAccessToken(UserDetails userDetails){
        Map<String,Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.toList()));
        return createToken(claims, userDetails.getUsername(), JwtTokenType.ACCESS,
                Duration.ofMinutes(securityProperties.getJwt().getAccessTokenMinutes()));
    }

    public String generateRefreshToken(String userName){
        Map<String,Object> claims = new HashMap<>();
        return createToken(claims, userName, JwtTokenType.REFRESH,
                Duration.ofDays(securityProperties.getJwt().getRefreshTokenDays()));
    }

    public long getAccessTokenExpirySeconds() {
        return Duration.ofMinutes(securityProperties.getJwt().getAccessTokenMinutes()).getSeconds();
    }

    public long getRemainingValidityMillis(String token) {
        long remaining = extractExpiration(token).getTime() - System.currentTimeMillis();
        return Math.max(remaining, 0);
    }

    private String createToken(Map<String, Object> claims, String userName, JwtTokenType tokenType, Duration ttl) {
        claims.put("typ", tokenType.getValue());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ttl.toMillis()))
                .setIssuer(securityProperties.getJwt().getIssuer())
                .setAudience(securityProperties.getJwt().getAudience())
                .setId(UUID.randomUUID().toString())
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private boolean isIssuerValid(Claims claims) {
        if (claims.getIssuer() == null) {
            return true;
        }
        return securityProperties.getJwt().getIssuer().equals(claims.getIssuer());
    }

    private boolean isAudienceValid(Claims claims) {
        if (claims.getAudience() == null) {
            return true;
        }
        return securityProperties.getJwt().getAudience().equals(claims.getAudience());
    }

    private boolean isTokenTypeValid(Claims claims, JwtTokenType expectedType) {
        Object rawType = claims.get("typ");
        if (rawType == null) {
            return expectedType == JwtTokenType.ACCESS;
        }
        return expectedType.getValue().equals(String.valueOf(rawType));
    }

    private Key getSignKey() {
        byte[] keyBytes= Decoders.BASE64.decode(securityProperties.getJwt().getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
