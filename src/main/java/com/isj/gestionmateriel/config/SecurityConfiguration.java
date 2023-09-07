package com.isj.gestionmateriel.config;

import java.io.IOException;

import org.keycloak.adapters.authorization.integration.jakarta.ServletPolicyEnforcerFilter;
import org.keycloak.adapters.authorization.spi.ConfigurationResolver;
import org.keycloak.adapters.authorization.spi.HttpRequest;
import org.keycloak.representations.adapters.config.PolicyEnforcerConfig;
import org.keycloak.util.JsonSerialization;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    // @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    // String jwkSetUri;

    private final KeycloakLogoutHandler keycloakLogoutHandler;
    private final KeycloakLoginSuccessHandler keycloakLoginSuccessHandler;

    private final JwtAuthConverter jwtAuthConverter;

    // SecurityConfiguration(KeycloakLogoutHandler keycloakLogoutHandler,
    // KeycloakLoginSuccessHandler keycloakLoginSuccessHandler, JwtAuthConverter
    // jwtAuthConverter) {
    // this.keycloakLogoutHandler = keycloakLogoutHandler;
    // this.keycloakLoginSuccessHandler = keycloakLoginSuccessHandler;
    // this.jwtAuthConverter = jwtAuthConverter;
    // }

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

        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(authorize -> authorize

                // admin
                .requestMatchers("/admin").hasRole("client_admin")
                .requestMatchers("/admin/*").hasRole("client_admin")
                // user
                .requestMatchers("/user").hasAnyRole(/*" Admin", "User", "realm_user", */"client_user", "client_admin" /*, "client-user"*/)
                .requestMatchers("/user/*").hasAnyRole("client_admin", "client_user")
                .requestMatchers("/*").permitAll()
                .requestMatchers("/login").authenticated()
                .anyRequest()
                .authenticated())
                .oauth2ResourceServer(oauth2 -> {
                    oauth2.jwt(jwt -> jwt
                            .jwtAuthenticationConverter(jwtAuthConverter));
                });
        // .addFilterAfter(createPolicyEnforcerFilter(),
        // BearerTokenAuthenticationFilter.class);;

        http.oauth2Login(login -> login
                .successHandler(keycloakLoginSuccessHandler))
                .logout(logout -> logout
                        // .logoutRequestMatcher("/logout")
                        .addLogoutHandler(keycloakLogoutHandler)
                        .logoutSuccessUrl("http://localhost:8081/"));

        return http.build();
    }
}