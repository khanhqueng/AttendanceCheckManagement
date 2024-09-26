package com.security.Jwt_service.service.impl;

import com.security.Jwt_service.config.security.CustomUserDetails;
import com.security.Jwt_service.exception.JwtValidateException;
import com.security.Jwt_service.service.JwtService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.security.Jwt_service.util.TokenType;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

import static com.security.Jwt_service.util.TokenType.ACCESS_TOKEN;
import static com.security.Jwt_service.util.TokenType.REFRESH_TOKEN;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.secretAccess}")
    private String secretAccess;
    @Value("${jwt.secretRefresh}")
    private String secretRefresh;
    @Value("${jwt.expiryMinutes}")
    private long expiryMinutes;
    @Value("${jwt.expiryHour}")
    private long expiryHour;

    @Override
    public String generateAccessToken(CustomUserDetails userDetails) {
        Date currentTime =new Date();
        Date expireTime= new Date(currentTime.getTime()+1000*60*expiryMinutes);
        return Jwts.builder()
                .subject(Long.toString(userDetails.getId()))
                .issuedAt(new Date())
                .expiration(expireTime)
                .signWith(key(ACCESS_TOKEN))
                .compact();
    }
    @Override
    public Long extractUserid(String token,TokenType type) {
        return getUserIdFromJwt(token,type);
    }

    @Override
    public boolean isValidAccessToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) key(ACCESS_TOKEN))
                    .build()
                    .parse(token);
            return true;
        }
        catch (MalformedJwtException exception){
            throw  new JwtValidateException(HttpStatus.BAD_REQUEST, "JWT is not valid");
        }
        catch (ExpiredJwtException exception){
            throw  new JwtValidateException(HttpStatus.BAD_REQUEST, "JWT is expired");
        }
        catch (UnsupportedJwtException exception){
            throw  new JwtValidateException(HttpStatus.BAD_REQUEST, "JWT is not supported");
        }
        catch (IllegalArgumentException exception){
            throw  new JwtValidateException(HttpStatus.BAD_REQUEST, "Jwt claims is null or empty");
        }
    }

    @Override
    public boolean isValidRefreshToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) key(REFRESH_TOKEN))
                    .build()
                    .parse(token);
            return true;
        }
        catch (MalformedJwtException exception){
            throw  new JwtValidateException(HttpStatus.BAD_REQUEST, "JWT is not valid");
        }
        catch (ExpiredJwtException exception){
            throw  new JwtValidateException(HttpStatus.BAD_REQUEST, "JWT is expired");
        }
        catch (UnsupportedJwtException exception){
            throw  new JwtValidateException(HttpStatus.BAD_REQUEST, "JWT is not supported");
        }
        catch (IllegalArgumentException exception){
            throw  new JwtValidateException(HttpStatus.BAD_REQUEST, "Jwt claims is null or empty");
        }
    }

    @Override
    public String generateRefreshToken(CustomUserDetails userDetails) {
        Date currentTime =new Date();
        Date expireTime= new Date(currentTime.getTime()+1000*60*60*expiryHour);
        return Jwts.builder()
                .subject(Long.toString(userDetails.getId()))
                .issuedAt(new Date())
                .expiration(expireTime)
                .signWith(key(REFRESH_TOKEN))
                .compact();
    }

    private Key key(TokenType type){
        if(ACCESS_TOKEN.equals(type)){
            return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretAccess));
        }
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretRefresh));
    }
    public Long getUserIdFromJwt(String token, TokenType type){
        if(ACCESS_TOKEN.equals(type)){
            return Long.parseLong( Jwts.parser()
                    .verifyWith((SecretKey) key(ACCESS_TOKEN))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject()
            );
        }
        return Long.parseLong( Jwts.parser()
                .verifyWith((SecretKey) key(REFRESH_TOKEN))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject()
        );


    }


}
