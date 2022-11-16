package com.example.javaproject.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.javaproject.models.User;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Component
public class JWTProvider {
    @Value("$(jwt.token.secret)")
    private String secret;

    public String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256("javathebest".getBytes());
        return JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(Date.from(LocalDate.now().plusDays(30).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .withClaim("role", user.getRole().getRoleName())
                .sign(algorithm);
    }

//    public boolean validateToken(String token) {
//        try {
//            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
//            return true;
//        } catch (Exception e) {
//
//        }
//        return false;
//    }

    public String getEmailFromToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256("javathebest".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String email = decodedJWT.getSubject();
        return email;
    }

    public String getRoleFromToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256("javathebest".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getClaim("role").asString();
    }
}
