package com.yim.asocream.security.filter.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.yim.asocream.model.user.UserEntity;
import com.yim.asocream.security.auth.PrincipalDetails;
import com.yim.asocream.user.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtAuthorizationFilter extends BasicAuthenticationFilter{


    private UserService service;


    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserService service) {
        super(authenticationManager);
        this.service = service;

    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String jwtheader = request.getHeader("Authorization");

        if(jwtheader == null || !jwtheader.startsWith("Bearer")) {
            chain.doFilter(request, response);
            return;
        }

        String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");

        String userEmail =
                JWT.require(Algorithm.HMAC512("codingwiki")).build().verify(jwtToken).getClaim("userEmail").asString();


        if(userEmail != null) {

            UserEntity user = service.findUserEmailAndUserEntity(userEmail);

            PrincipalDetails principalDetails = new PrincipalDetails(user);

            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            SecurityContextHolder.getContext().getAuthentication();

            request.setAttribute("userEmail",userEmail);

            chain.doFilter(request, response);
        }
    }
}
