
package com.medvault;

import jakarta.servlet.DispatcherType;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            CustomUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }

    // Provides AuthenticationManager for the login API
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            DaoAuthenticationProvider authenticationProvider,
            CustomAuthenticationEntryPoint authenticationEntryPoint)
            throws Exception {

        http
            .authenticationProvider(authenticationProvider)

            .exceptionHandling(exception -> exception
                .authenticationEntryPoint(authenticationEntryPoint)
            )

            .authorizeHttpRequests(auth -> auth
                // Allow Spring Boot error handling
                .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()

                // Allow registration without authentication
                .requestMatchers(
                    HttpMethod.POST,
                    "/api/users/register"
                ).permitAll()

                // Allow login without prior authentication
                .requestMatchers(
                    HttpMethod.POST,
                    "/api/users/login"
                ).permitAll()

                // Protect all other endpoints
                .anyRequest().authenticated()
            )

            .httpBasic(basic -> basic
                .authenticationEntryPoint(authenticationEntryPoint)
            )

            .csrf(csrf -> csrf.disable());

        return http.build();
    }
}
