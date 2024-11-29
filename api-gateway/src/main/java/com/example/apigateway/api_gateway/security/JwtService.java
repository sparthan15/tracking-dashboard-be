package com.example.apigateway.api_gateway.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
class JwtService implements TokenProvider {

    @Value("${spring.security.jwt.secret-key}")
    private String secretKey;

    @Value("${spring.security.jwt.token-expiration-seconds}")
    private long tokenExpiration;

    String extractUsername(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    List<String> extractRoles(String jwt) {
        return extractClaim(jwt, claims -> (List<String>) claims.get("roles"));
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(Map.of(), userDetails);
    }

    boolean isTokenValid(String jwt) {
        return !isTokenExpired(jwt);
    }

    private boolean isTokenExpired(String jwt) {
        return extractClaim(jwt, Claims::getExpiration).before(new Date());
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        long currentTimeMillis = System.currentTimeMillis();
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .map(role -> role.substring("ROLE_".length()))
                        .toArray())
                .setIssuedAt(new Date(currentTimeMillis))
                .setExpiration(new Date(currentTimeMillis + tokenExpiration * 1000))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private <T> T extractClaim(String jwt, Function<Claims, T> claimResolver) {
        Claims claims = extractAllClaims(jwt);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String jwt) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (JwtException e) {
            throw new JwtAuthenticationException(e.getMessage());
        }
    }

    private SecretKey getSigningKey() {
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }
}
