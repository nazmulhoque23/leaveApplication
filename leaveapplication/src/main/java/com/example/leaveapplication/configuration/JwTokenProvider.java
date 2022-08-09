package com.example.leaveapplication.configuration;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
public class JwTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwTokenProvider.class);


    @Value("${application.jwt.secret}")
    private String jwtSecret;

    @Value("${application.jwt.expirationInMs}")
    private int jwtExpirationInMs;

    @Value("${application.jwt.tokenPrefix}")
    private String tokenPrefix;



    public String generateToken(Authentication auth) {
        UserDetails userPrincipal = (UserDetails) auth.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime()+jwtExpirationInMs);

        String token = Jwts.builder()
                    .setSubject(userPrincipal.getClass().getName())
                    .setIssuedAt(new Date())
                    .setExpiration(expiryDate)
                    .signWith(SignatureAlgorithm.HS512, jwtSecret)
                    .compact();

        return (tokenPrefix+" "+token);
    }

    public String getUserFromJWT(String token){
        Claims claims = Jwts.parser()
                        .setSigningKey(jwtSecret)
                        .parseClaimsJws(token)
                        .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String authToken){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        }catch (SignatureException ex){
            logger.error("Invalid JWT Signature");
        }catch (MalformedJwtException ex){
            logger.error("Invalid JWT token");
        }catch (ExpiredJwtException ex){
            logger.error("JWT token expired");
        }catch (UnsupportedJwtException ex){
            logger.error("Unsupported JWT token");
        }catch (IllegalArgumentException ex){
            logger.error("JWT claims string is empty");
        }

        return false;
    }
}
