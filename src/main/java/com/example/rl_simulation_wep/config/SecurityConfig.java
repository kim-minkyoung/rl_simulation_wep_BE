package com.example.rl_simulation_wep.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public RequestCache requestCache() {
        return new HttpSessionRequestCache();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/users").permitAll() // POST /users 허용
                        .requestMatchers(HttpMethod.GET, "/users").permitAll() // GET /users 허용
                        .requestMatchers(HttpMethod.GET, "/users/{userId}").permitAll() // GET /users/{userId} 허용
                        .requestMatchers("/login").permitAll() // 로그인 페이지 허용
                        .requestMatchers("/").permitAll()
                        .anyRequest().authenticated() // 나머지 요청은 인증 필요
                )
                .formLogin(form -> form
//                        .loginPage("/login") // 로그인 페이지 URL
                        .loginProcessingUrl("/perform_login") // 로그인 요청 URL
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .successHandler(customAuthenticationSuccessHandler(requestCache())) // 성공 시 핸들러
                        .failureHandler(customAuthenticationFailureHandler()) // 실패 시 핸들러
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/perform_logout") // 로그아웃 요청 URL
                        .logoutSuccessHandler(customLogoutSuccessHandler())
                        .permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable); // CSRF 보호 비활성화

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler(RequestCache requestCache) {
        return (request, response, authentication) -> {
            SavedRequest savedRequest = requestCache.getRequest(request, response);
            String targetUrl = savedRequest != null ? savedRequest.getRedirectUrl() : request.getContextPath() + "/";

            response.sendRedirect(targetUrl); // 원래 요청했던 URL로 리다이렉트
        };
    }

    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return (request, response, exception) -> {
            response.sendRedirect(request.getContextPath() + "/login?error=true"); // 실패 시 리다이렉트
        };
    }

    @Bean
    public LogoutSuccessHandler customLogoutSuccessHandler() {
        return (request, response, authentication) -> {
            response.setContentType("application/json"); // 응답 타입 설정
            response.setCharacterEncoding("UTF-8"); // 문자 인코딩 설정
            response.getWriter().write("{\"message\": \"로그아웃 성공\"}"); // JSON 형식으로 메시지 작성
            response.getWriter().flush(); // 응답 전송
        };
    }
}
