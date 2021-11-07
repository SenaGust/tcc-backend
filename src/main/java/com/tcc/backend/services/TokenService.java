package com.tcc.backend.services;

import com.tcc.backend.models.User;
import com.tcc.backend.resources.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {
    @Value("${backend.jwt.expiration}")
    private String expirationTime;
    @Value("${backend.jwt.secret}")
    private String secret;
    @Value("${backend.application-name}")
    private String applicationName;
    @Autowired
    private UserRepository userRepository;

    public String create(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        Date today = new Date();
        Date expirationDate = new Date(today.getTime() + Long.parseLong(expirationTime));
        return Jwts.builder()
                .setIssuer(applicationName)
                .setSubject(user.getId().toString())
                .setIssuedAt(today)
                .setExpiration(expirationDate)
                .claim("name", user.getName())
                .claim("lastname", user.getLastname())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public boolean isValid(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Integer getIdUserByToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return Integer.parseInt(claims.getSubject());
    }
}
