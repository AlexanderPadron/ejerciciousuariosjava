package com.testalex.helper;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Slf4j
@Component
public class JwtHelper {

    private static final String ISSUER = "auth0";
    private static final String SECRET = "@A2l3e4x5a6n7d7e8rP9a0d4r56o4n2021T5e7s8t8J9a9v9a9";
    private static final Integer EXPIRES = 6;

    public String createToken(String data) {
        return JWT.create()
                .withIssuer(ISSUER)
                .withExpiresAt(expire(EXPIRES))
                .withClaim(ISSUER, data)
                .sign(Algorithm.HMAC512(SECRET));
    }

    public String validateToken(String token) {
        JWTVerifier jwtVerifier = JWT
                .require(Algorithm.HMAC512(SECRET))
                .withIssuer(ISSUER)
                .build();
        DecodedJWT decodedJWT = jwtVerifier.verify(cleanToken(token));
        String data = decodedJWT.getClaim(ISSUER).asString();
        return String.valueOf(data);
    }

    private Date expire(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }

    private String cleanToken(String token) {
        String aux = token;
        if (aux != null && aux.contains("Bearer")) {
            aux = aux.replace("Bearer ", "");
            aux = aux.trim();
        }
        return aux;
    }
}
