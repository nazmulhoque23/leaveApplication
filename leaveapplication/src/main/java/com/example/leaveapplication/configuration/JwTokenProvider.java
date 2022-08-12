package com.example.leaveapplication.configuration;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
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

    /*@Value("${application.jwt.jwtCookieName}")
    private String jwtCookie;*/

    /*public String getJwtFromCookies(HttpServletRequest request){
        Cookie cookie = WebUtils.getCookie(request, jwtCookie);

        if(cookie != null){
            return cookie.getValue();
        }
        else{
            return null;
        }

    }

    public ResponseCookie generateJwtCookie(UserDetails userPrincipal){
        String jwt = generateToken(userPrincipal.getUsername());
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt).path("/api/auth").maxAge(24 * 60 * 60).httpOnly(true).build();
        return cookie;
    }

    public ResponseCookie getCleanJwtCookie(){
        ResponseCookie cookie =  ResponseCookie.from(jwtCookie, null).path("/api/auth").build();
        return cookie;
    }*/


    public String generateToken(Authentication auth) {
        CustomUserDetails userDetails  = (CustomUserDetails) auth.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime()+jwtExpirationInMs);

        String token = Jwts.builder()
                    .setSubject(userDetails.getUsername())
                    .setIssuedAt(new Date())
                    .setExpiration(expiryDate)
                    .signWith(SignatureAlgorithm.HS512, jwtSecret)
                    .compact();

        return token;
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
