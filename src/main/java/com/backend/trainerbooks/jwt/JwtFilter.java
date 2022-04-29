package com.backend.trainerbooks.jwt;

import com.backend.trainerbooks.services.GroupUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.backend.trainerbooks.enums.JWTEnum.AUTHORIZATION;
import static com.backend.trainerbooks.enums.JWTEnum.BEARER;

@Component
public class JwtFilter{

    private JWTUtils jwtUtils;
    private GroupUserDetailsService groupUserDetailsService;

    @Autowired
    public JwtFilter(JWTUtils jwtUtils, GroupUserDetailsService groupUserDetailsService) {
        this.jwtUtils = jwtUtils;
        this.groupUserDetailsService = groupUserDetailsService;
    }


//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String authorization  = request.getHeader(AUTHORIZATION.getValue());
//        String token = null;
//        String username = null;
//
//        if(authorization != null && authorization.startsWith(BEARER.getValue())) {
//            token = authorization.substring(BEARER.getValue().length());
//            username = jwtUtils.getUsernameFromToken(token);
//        }
//
//        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = groupUserDetailsService.loadUserByUsername(username);
//
//            if(jwtUtils.validateToken(token,userDetails)) {
//                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
//                        userDetails,null,userDetails.getAuthorities());
//                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)
//                );
//                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//            }
//
//        }
//        //Handle CORS
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT,OPTIONS");
//        response.setHeader("Access-Control-Allow-Headers", "*");
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        response.setHeader("Access-Control-Max-Age", "180");
//        filterChain.doFilter(request,response);
//    }
}
