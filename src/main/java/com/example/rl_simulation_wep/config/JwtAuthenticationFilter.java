package com.example.rl_simulation_wep.config;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil, CustomUserDetailsService customUserDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        final String authorizationHeader = request.getHeader("Authorization");

        Long userId = null;
        String jwt = null;

        // Authorization 헤더가 있는지 로그
        System.out.println("Authorization header: " + authorizationHeader);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            System.out.println("Extracted JWT: " + jwt);

            try {
                userId = Long.valueOf(JwtTokenUtil.extractUserId(jwt));
                System.out.println("Extracted userId: " + userId);
            } catch (ExpiredJwtException e) {
                // 토큰 만료 처리
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token has expired.");
                System.out.println("Token has expired.");
                return; // 요청 처리 중단
            } catch (Exception e) {
                // 기타 예외 처리
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token.");
                System.out.println("Invalid token.");
                return; // 요청 처리 중단
            }
        } else {
            System.out.println("No JWT token found.");
        }

        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            CustomUserDetails userDetails = (CustomUserDetails) this.customUserDetailsService.loadUserByUsername(String.valueOf(userId));
            System.out.println("Loaded user details for email: " + userId);

            if (!jwtTokenUtil.isTokenExpired(jwt)) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                System.out.println("Authentication set in SecurityContextHolder.");
            } else {
                System.out.println("JWT token is expired.");
            }
        }

        chain.doFilter(request, response);
    }


}
