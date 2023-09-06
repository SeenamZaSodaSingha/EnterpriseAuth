package com.isj.gestionmateriel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final KeycloakLogoutHandler keycloakLogoutHandler;
    private final KeycloakLoginSuccessHandler keycloakLoginSuccessHandler;

    SecurityConfiguration(KeycloakLogoutHandler keycloakLogoutHandler, KeycloakLoginSuccessHandler keycloakLoginSuccessHandler) {
        this.keycloakLogoutHandler = keycloakLogoutHandler;
        this.keycloakLoginSuccessHandler = keycloakLoginSuccessHandler;
    }

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

        http.authorizeRequests()
        //admin
                .requestMatchers("/api/v1/admin/**").hasRole("client_admin")
        //user
                .requestMatchers("/api/v1/user/**").hasAnyRole("client_admin", "client_user")
        //public
                .requestMatchers("/login").authenticated()
                .requestMatchers("/*").permitAll()
                .anyRequest()
                .authenticated();

        http.oauth2Login()
                .successHandler(keycloakLoginSuccessHandler)
                .and()
                .logout()
                .addLogoutHandler(keycloakLogoutHandler)
                .logoutSuccessUrl("/");
        return http.build();
    }


}
