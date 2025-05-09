package com.audicin.backend.track.api.security.services;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private final String secretKey;
    private final long jwtExpiration;

    @Autowired
    public JwtService(Dotenv dotenv) {
        this.secretKey = dotenv.get("security.jwt.secret-key");
        this.jwtExpiration =
                Long.parseLong(dotenv.get("security.jwt.expiration-time"));
    }


    public String extractUsername(final String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(final String token,
                              final Function<Claims,T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(final UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(final Map<String,Object> extraClaims,
                                final UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    public long getExpirationTime() {
        return jwtExpiration;
    }

    private String buildToken(final Map<String,Object> extraClaims,
                              final UserDetails userDetails,
                              final long expiration) {
        return Jwts.builder().setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(
                        new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
    }

    public boolean isTokenValid(final String token,
                                final UserDetails userDetails) {
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) &&
                !isTokenExpired(token);
    }

    private boolean isTokenExpired(final String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(final String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(final String token) {
        return Jwts.parser().setSigningKey(getSignInKey()).build()
                .parseClaimsJws(token).getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
