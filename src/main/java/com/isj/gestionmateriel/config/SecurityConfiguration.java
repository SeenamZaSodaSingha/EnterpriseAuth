package com.isj.gestionmateriel.config;

import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration  {
    private final JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable());
        http.cors();
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/user").hasAnyRole("Client-Admin", "Client-User")
                .requestMatchers("/user/**").hasAnyRole("Client-Admin", "Client-User")
                .requestMatchers("/admin").hasRole("Client-Admin")
                .requestMatchers("/admin/**").hasRole("Client-Admin")
                .requestMatchers("/*").hasAnyRole("Client-Admin", "Client-User"))
                .oauth2ResourceServer(oauth2 -> {
                    oauth2.jwt(jwt -> jwt
                            .jwtAuthenticationConverter(jwtAuthConverter));
                });
        return http.build();
    }
}