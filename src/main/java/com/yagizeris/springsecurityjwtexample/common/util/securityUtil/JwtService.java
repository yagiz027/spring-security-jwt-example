package com.yagizeris.springsecurityjwtexample.common.util.securityUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/*
 * Bu sınıf Jwt Tokenimiz içerisindeki değerleri parse edip username'in verilerine erişmemizi,
 * Token Generate etmemizi,
 * Request yapan kullanıcının şifresini kırabilmemizi sağlayan işlemleri içeren bir service 
 * sınıfıdır.
 */
@Service
public class JwtService {
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    public String findUsername(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    /*
    * Jwt içerisindeki tüm claim'leri çeken method
    * */
    private Claims extractAllClaims(String jwt){
        return Jwts.parserBuilder()
                .setSigningKey(getKey()).build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    /*
    * Jwt içerisinden belirtilen claim'i çeken method
     */
    public String extractEmail(String jwt){
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody()
                .toString()
                .split(",")[1]
                .substring(7);
    }

    /*
    Token generate eden method
     */

    public String generateToken(Map<String,Object> extractClaims, UserDetails userDetails){
        return Jwts.builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+jwtExpiration))
                .signWith(getKey(),SignatureAlgorithm.HS256)
                .compact();
    }
    
    /*
        Bu method jwt içerisindeki username'in veritabanından request ile gelen username'e eşit olup olmadığını kontrol etmektedir.
        Ayrıca gelen jwt içerisindeki claim'lerin expiration date'i nin geçerli olup olmadığını da kontrol etmektedir.
     */
    public boolean isTokenValid(String jwt,UserDetails userDetails){
        final String username = findUsername(jwt);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(jwt);

    }
    /*
    Token'in son kullanım tarihini geçip geçmediğini kontrol eden method
     */

    private boolean isTokenExpired(String jwt) {
        return extractExpiration(jwt).before(new Date());
    }
    /*
    Token'in expritaion claim'ini çeken method
     */
    private Date extractExpiration(String jwt) {
        return  extractClaim(jwt,Claims::getExpiration);
    }

    /*
    Token içerisinden belirtilen claim'i çekmemizi sağlayan method
     */

    private <T> T extractClaim(String jwt, Function<Claims,T> claimsResolver) {
        final Claims claims = extractAllClaims(jwt);
        return claimsResolver.apply(claims);
    }

    /*
    Token'e secret key atayan method
     */
    private Key getKey() {
        byte[] key = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(key);
    }
}
