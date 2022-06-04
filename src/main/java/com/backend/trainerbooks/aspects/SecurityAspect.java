package com.backend.trainerbooks.aspects;

import com.backend.trainerbooks.exceptions.AuthException;
import com.backend.trainerbooks.jwt.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static com.backend.trainerbooks.enums.JWTEnum.AUTHORIZATION;

@Aspect
@EnableAspectJAutoProxy
@Component
@RequiredArgsConstructor
public class SecurityAspect {
    Logger logger = LoggerFactory.getLogger(SecurityAspect.class);

    private final JWTUtils jwtUtils;

    @Before(value = "@within(com.backend.trainerbooks.annotations.SecuredEndPoint) || @annotation(com.backend.trainerbooks.annotations.SecuredEndPoint))")
    public void validateToken(JoinPoint joinPoint) throws AuthException {
        Object[] signatureArgs = joinPoint.getArgs();
        HttpServletRequest request = (HttpServletRequest) signatureArgs[0];
        String userJwtToken = null;
        try{
            userJwtToken = request.getHeader(AUTHORIZATION.getValue());
            if(jwtUtils.getIdFromToken(userJwtToken) == null) {
                throw new AuthException("Authentication Failed.");
            }
            Boolean isExpired = jwtUtils.isTokenExpired(userJwtToken);
            if (Boolean.TRUE.equals(isExpired)) {
                logger.info("Exception in validateToken token not valid : " + userJwtToken);
                throw new AuthException("Token is expired!");
            }
        }catch (Exception e) {
            throw new AuthException("Authentication Failed , Not such header 'Authorization'.");
        }



    }
}
