package com.isj.gestionmateriel.config;

import java.io.IOException;

import org.keycloak.adapters.authorization.integration.jakarta.ServletPolicyEnforcerFilter;
import org.keycloak.adapters.authorization.spi.ConfigurationResolver;
import org.keycloak.adapters.authorization.spi.HttpRequest;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.representations.adapters.config.PolicyEnforcerConfig;
import org.keycloak.util.JsonSerialization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration  {

    @Autowired
    private SessionFilter sessionFilter;
    private final JwtAuthConverter jwtAuthConverter;

    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(buildSessionRegistry());
    }

    @Bean
    protected SessionRegistry buildSessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // http.addFilterBefore(sessionFilter, UsernamePasswordAuthenticationFilter.class);
        http.csrf(csrf -> csrf.disable());
        http.cors();
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/v1/user").hasAnyRole("Client-Admin", "Client-User")
                .requestMatchers("/api/v1/user/**").hasAnyRole("Client-Admin", "Client-User")
                .requestMatchers("/api/v1/admin").hasRole("Client-Admin")
                .requestMatchers("/api/v1/admin/**").hasRole("Client-Admin")
                .requestMatchers("/*").hasAnyRole("Client-Admin", "Client-User"))
                .oauth2ResourceServer(oauth2 -> {
                    oauth2.jwt(jwt -> jwt
                            .jwtAuthenticationConverter(jwtAuthConverter));
                });
        http.addFilterBefore(sessionFilter, BearerTokenAuthenticationFilter.class);
        return http.build();
    }
}