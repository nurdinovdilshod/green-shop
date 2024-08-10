package com.company.config.security;

import com.company.payload.res.GenericError;
import com.company.payload.res.GenericResponse;
import com.company.repositories.AuthUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletOutputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true
)
public class SecurityConfig {
    private final ObjectMapper objectMapper;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthUserRepository authUserRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        return security.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req.requestMatchers(
                                "/api/auth/**",
                                "/swagger-ui/**",
                                "v3/api-docs/**",
                                "/api/v1/files/download/**",
                                "**")
                        .permitAll()
                        .anyRequest()
                        .fullyAuthenticated())
                .sessionManagement(p -> p.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(e -> e.authenticationEntryPoint(authenticationEntryPoint())
                        .accessDeniedHandler(accessDeniedHandler()))
                .addFilterBefore(new JwtTokenFilter(jwtTokenUtil, userDetailsService()), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    private AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            accessDeniedException.printStackTrace();
            GenericResponse<GenericError> genericResponse = new GenericResponse<>(
                    new GenericError(request.getRequestURI(),
                            accessDeniedException.getMessage(),
                            403)
            );
            response.setStatus(403);
            ServletOutputStream responseOutputStream = response.getOutputStream();
            objectMapper.writeValue(responseOutputStream, genericResponse);
        };
    }

    private AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            authException.printStackTrace();
            GenericResponse<GenericError> genericResponse = new GenericResponse<>(
                    new GenericError(request.getRequestURI(),
                            authException.getMessage(),
                            403)
            );
            response.setStatus(500);
            ServletOutputStream responseOutputStream = response.getOutputStream();
            objectMapper.writeValue(responseOutputStream, genericResponse);
        };

    }

    @Bean
    public UserDetailsService userDetailsService() {
        return phoneNumber -> new CustomUserDetails(authUserRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService());
        return authenticationProvider;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(
                List.of("http://localhost:8080",
                        "http://localhost:3000",
                        "http://192.168.0.112:8080",
                        "http://192.168.0.105:3000",
                        "http://localhost:9095"));
        configuration.setAllowedHeaders(List.of("*",
                "Accept", "Content-Type", "Authorization"
        ));
        configuration.setAllowedMethods(List.of("PUT", "DELETE", "POST", "GET"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(authenticationProvider());
    }
}
