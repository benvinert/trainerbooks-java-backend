package com.backend.trainerbooks.aspects;

import com.backend.trainerbooks.jwt.JWTUtils;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import java.util.Objects;

import static com.backend.trainerbooks.enums.JWTEnum.AUTHORIZATION;

@Aspect
@EnableAspectJAutoProxy
@Component
@RequiredArgsConstructor
public class SecurityAspect {

    private final JWTUtils jwtUtils;

    @Before(value = "@within(com.backend.trainerbooks.annotations.SecureEndPoint) || @annotation(com.backend.trainerbooks.annotations.SecureEndPoint))")
    public void validateToken(JoinPoint joinPoint) {
        Object[] signatureArgs = joinPoint.getArgs();
        HttpServletRequest request = (HttpServletRequest) signatureArgs[0];
        try{
            String userJwtToken = request.getHeader(AUTHORIZATION.getValue());
            Objects.requireNonNull(jwtUtils.getIdFromToken(userJwtToken));
            Boolean isExpired = jwtUtils.isTokenExpired(userJwtToken);
            if(Boolean.TRUE.equals(isExpired)) {
                throw new JwtException("Token is expired!");
            }
        }catch (Exception e) {
            e.getMessage();
        }
    }
}
