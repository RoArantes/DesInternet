package org.example.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys; // Para chaves mais seguras
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct; // Para inicializar a chave
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecretString;

    @Value("${jwt.expiration}")
    private long jwtExpirationInMs;

    private Key secretKey;

    @PostConstruct
    protected void init() {
        // Decodifica a string da chave secreta para bytes e cria um objeto Key
        // É mais seguro gerar a chave assim do que usar a string diretamente.
        // Para HS256, a chave deve ter pelo menos 256 bits (32 bytes).
        // Se sua jwt.secret for menor, você terá um erro.
        // Considere usar Keys.secretKeyFor(SignatureAlgorithm.HS256) para gerar uma chave na primeira vez
        // e armazená-la de forma segura.
        this.secretKey = Keys.hmacShaKeyFor(jwtSecretString.getBytes());
    }

    public String generateToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        return generateTokenFromUsername(userPrincipal.getUsername());
    }

    public String generateTokenFromUsername(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey, SignatureAlgorithm.HS256) // Use o objeto Key
                .compact();
    }


    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey) // Use o objeto Key
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(authToken); // Use o objeto Key
            return true;
        } catch (Exception ex) {
            // Logar a exceção seria uma boa prática aqui
            // ex: io.jsonwebtoken.SignatureException: JWT signature does not match locally computed signature.
            // ex: io.jsonwebtoken.MalformedJwtException: JWT strings must contain exactly 2 period characters.
            // ex: io.jsonwebtoken.ExpiredJwtException: JWT expired
            // ex: io.jsonwebtoken.UnsupportedJwtException: JWT handler does not support payoffs of type: class java.lang.String
            // ex: IllegalArgumentException: JWT String argument cannot be null or empty or whitespace
            System.err.println("Invalid JWT token: " + ex.getMessage());
        }
        return false;
    }
}