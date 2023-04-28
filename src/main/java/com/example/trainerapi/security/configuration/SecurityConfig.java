package com.example.trainerapi.security.configuration;

import com.example.trainerapi.security.filters.JwtAuthenticationFilter;
import com.example.trainerapi.security.filters.RequestFilter;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * Configuration for which endpoints require authentication and which do not.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    private final RequestFilter requestFilter;

    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, AuthenticationProvider authenticationProvider, RequestFilter requestFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationProvider = authenticationProvider;
        this.requestFilter = requestFilter;
    }

    /**
     * Configure which endpoints require authentication and which do not.
     * Stateless sessions are used because JWTs are used for authentication.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/users").permitAll()
                .requestMatchers("/hello").permitAll()
                .requestMatchers("/api/quotes/**").permitAll()
                .requestMatchers("/api/auth/register", "/api/auth/authenticate").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(requestFilter, jwtAuthenticationFilter.getClass());

        http.httpBasic();
        return http.build();
    }


}
