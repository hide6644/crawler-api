package crawlerapi.security;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import crawlerapi.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public final class JWTUtil implements Serializable {

    @Value("${crawler-api.jjwt.expiration}")
    private String expirationTime;

    private SecretKey key = Jwts.SIG.HS512.key().build();

    public Claims getAllClaimsFromToken(final String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }

    public String getUsernameFromToken(final String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public Date getExpirationDateFromToken(final String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    private Boolean isTokenExpired(final String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(final User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRoles());
        return doGenerateToken(claims, user.getUsername());
    }

    private String doGenerateToken(final Map<String, Object> claims, final String username) {
        final Long expirationTimeLong = Long.parseLong(expirationTime); // in second

        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong * 1000);
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(createdDate)
                .expiration(expirationDate)
                .signWith(key)
                .compact();
    }

    public Boolean validateToken(final String token) {
        return !isTokenExpired(token);
    }
}
