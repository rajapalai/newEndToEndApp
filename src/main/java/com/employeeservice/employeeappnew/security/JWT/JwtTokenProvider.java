package com.employeeservice.employeeappnew.security.JWT;

import com.employeeservice.employeeappnew.exception.JwtValidateApiException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private String jwtSecret;
    private String jwtExpiration;

    public String generateToken(Authentication authentication) {
        String userName = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpiration);

        String jwtToken = Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(key())
                .compact();

        return jwtToken;
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    //Get userName from jwt token
    public String getUserName(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
        String userName = claims.getSubject();
        return userName;
    }

    //Validate JWT token
    public Boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token);
            return true;
        }catch (MalformedJwtException e) {
            throw new JwtValidateApiException(HttpStatus.BAD_REQUEST, "Invalid JWT token!..");
        }catch (ExpiredJwtException e) {
            throw new JwtValidateApiException(HttpStatus.BAD_REQUEST, "Expired JWT token!..");
        }catch (UnsupportedJwtException e) {
            throw new JwtValidateApiException(HttpStatus.BAD_REQUEST, "Unsupported JWT token!..");
        }catch (IllegalArgumentException e){
            throw new JwtValidateApiException(HttpStatus.BAD_REQUEST, "JWT claims string is emplty!..");
        }
    }
}
