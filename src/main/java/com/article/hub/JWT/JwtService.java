package com.article.hub.JWT;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component  // this will scan it and create the bean of JwtService so that we can use it anywhere in our application.
public class JwtService {

    public static final String SECRET = "nbf8948t0e9tn8dby3940nf9w0450wjm=i3io1in40wjhdgod49895j3wner3";


    // method to pass the value inside the "createToken" to generate the token [value will be: username and claims(in claim we'll put role)]
    public String generateToken(String  usernamme) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, usernamme);
    }


    /** method to create the jwt token
     here we have to simply pass the subject or claims to generate the token */
    private String createToken(Map<String, Object> claims, String userName){
//        byte[] apiKeySecretBytes = secret.getBytes();  // Convert to byte array to ensure proper encoding
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // 1000ms * 60s * 120m = 2hr (ie. 1000ms * 60s = 1min, 1min * 120min = 120min = 2hr)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 120))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    // we'll use the "isTokenExpired" and "extractUsername" method to validate the token if it is not expired
    public Boolean validateToken(String token, UserDetails userDetails) {
        final  String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    // Logic to extract the userName
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject); //we'll set the username and getSubject is key here So we'll write "getSubject"
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    private Claims extractAllClaims(String token){
    return Jwts
            .parserBuilder()
            .setSigningKey(getSignKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }
}
