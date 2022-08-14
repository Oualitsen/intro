package com.pinitservicfes.intro.filters;

import org.springframework.stereotype.Component;

import com.pinitservicfes.intro.model.users.User;
import com.pinitservicfes.intro.model.users.UserBase;

import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {

    public static final String SECRET = "TcwbewNUck4rUkw23JUUSSAe7ryrCJXwCfvLZS84+Vg4Or1WWYDY6kM430aJOvU1KAQQKSs2xFd9ImCE+hocyA==";

    public String createToken(UserBase basicUser) {
        return createToken(from(basicUser));
    }

    public String createToken(User user) {
        var now = Instant.now();

        var hmacKey = new SecretKeySpec(Base64.getDecoder().decode(SECRET), SignatureAlgorithm.HS256.getJcaName());
        return Jwts.builder().setClaims(user.toMap()).setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(7 * 30, ChronoUnit.DAYS))).signWith(hmacKey).compact();
    }

    public User from(UserBase user) {
        return User.builder().userId(user.getId()).roles(user.getRoles()).build();
    }

    public User parse(String token) {

        final Jwt jwt = Jwts.parserBuilder().setSigningKey(SECRET).build().parse(token);

        var claims = (Claims) jwt.getBody();

        return User.builder()
                .userId((String) claims.get("id"))
                .roles(((List<String>) claims.get("roles")))
                .build();
    }
}
