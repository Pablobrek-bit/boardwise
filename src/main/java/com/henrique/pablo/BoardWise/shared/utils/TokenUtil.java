package com.henrique.pablo.BoardWise.shared.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.Role;
import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.User;
import org.springframework.stereotype.Service;

@Service
public class TokenUtil {

    private static final String SECRET = "secret";

    public static String generateToken(User user){
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        return JWT.create()
                .withIssuer("BoardWise")
                .withSubject(String.valueOf(user.getId()))
                .withClaim("roles",
                        user.getRoles().stream().map(Role::getAuthority).toList())
                .sign(algorithm);
    }

    public DecodedJWT decodeToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            return JWT.require(algorithm).build().verify(token);
        } catch (Exception e){
            return null;
        }
    }

    public boolean tokenIsValid(String token){
        return decodeToken(token) != null;
    }

}
