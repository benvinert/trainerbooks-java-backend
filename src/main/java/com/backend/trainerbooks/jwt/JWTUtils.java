package com.backend.trainerbooks.jwt;

import com.backend.trainerbooks.entitys.UserDAO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.backend.trainerbooks.enums.JWTEnum.BEARER;

@Component
public class JWTUtils implements Serializable {
    private static final long serialVersionUID = 234234523523L;

    public static final long JWT_TOKEN_VALIDITY =   30 * 24 * 60 * 60;

    @Value("${jwt.secret}")
    private String secretKey;

    /**
     * Return Username or Id from user.
     * @param token
     * @param indexIdOrUsername 0 is username , 1 is id.
     * @return
     */
    private String getUsernameOrIdFromToken(String token,int indexIdOrUsername) {
        token = cutBearer(token);
        String payload = getClaimFromToken(token, Claims::getSubject);
        String[] payloadList = payload.split(",");//Payload Example : [benoo,5] benoo is username , 5 is id.
        return payloadList[indexIdOrUsername];
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }


    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }


    //for retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    private String cutBearer(String token) {
        if(token.startsWith(BEARER.getValue())) {
            token = token.substring(BEARER.getValue().length());
        }
        return token;
    }


    //check if the token has expired
    public Boolean isTokenExpired(String token) {
        token = cutBearer(token);
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public String generateToken(UserDAO userDAO) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDAO.getEmail() + "," + userDAO.getId());
    }


    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }


    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Long getIdFromToken(String token) {
        return Long.parseLong(getUsernameOrIdFromToken(token,1));
    }

    public String getUsernameFromToken(String token) {
        return getUsernameOrIdFromToken(token,0);
    }
}
