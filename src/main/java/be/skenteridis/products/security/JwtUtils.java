package be.skenteridis.products.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtils {
    private final Key key;
    private final long expiration;
    public JwtUtils(@Value("${app.jwt.secret}") String key, @Value("${app.jwt.expiration}") long expiration) {
        this.expiration = expiration;
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(key));
    }

    public String generate(String subject, Map<String, Object> claims) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expiration);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key)
                .compact();
    }

    public Jws<Claims> parse(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }

    public String getSubject(String token) {
        return parse(token).getBody().getSubject();
    }

    public boolean isValid(String token) {
        try {
            return new Date().before(parse(token).getBody().getExpiration());
        } catch (Exception e) {
            return false;
        }
    }
}
