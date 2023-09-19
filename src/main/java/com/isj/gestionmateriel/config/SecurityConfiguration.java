package com.isj.gestionmateriel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration  {
    private final KeycloakLogoutHandler keycloakLogoutHandler;
    private final KeycloakLoginSuccessHandler keycloakLoginSuccessHandler;

    private final JwtAuthConverter jwtAuthConverter;

    // SecurityConfiguration(KeycloakLogoutHandler keycloakLogoutHandler,
    //         KeycloakLoginSuccessHandler keycloakLoginSuccessHandler, JwtAuthConverter jwtAuthConverter) {
    //     this.keycloakLogoutHandler = keycloakLogoutHandler;
    //     this.keycloakLoginSuccessHandler = keycloakLoginSuccessHandler;
    //     this.jwtAuthConverter = jwtAuthConverter;
    // }

    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(buildSessionRegistry());
    }


    @Bean
    protected SessionRegistry buildSessionRegistry() {
        return new SessionRegistryImpl();
    }

    // @Bean(name = DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)
    // public DispatcherServlet dispatcherServlet() {
    //     DispatcherServlet dispatcherServlet = new DispatcherServlet();
    //     dispatcherServlet.setDispatchOptionsRequest(true);
    //     return dispatcherServlet;
    // }

    

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable());
        // http.cors(cors -> cors.disable());
        http.cors();

        http.authorizeHttpRequests(authorize -> authorize

                .requestMatchers("/api/v1/user").hasAnyRole("Client-Admin", "Client-User")
                .requestMatchers("/api/v1/user/**").hasAnyRole("Client-Admin", "Client-User")
                .requestMatchers("/api/v1/admin").hasRole("Client-Admin")
                .requestMatchers("/api/v1/admin/**").hasRole("Client-Admin")
                .requestMatchers("/*").permitAll()
                .anyRequest()
                .authenticated())
                // .csrf(csrf -> csrf.disable())
                // .cors(cors -> cors.disable())
                .oauth2ResourceServer(oauth2 -> {
                    oauth2.jwt(jwt -> jwt
                            .jwtAuthenticationConverter(jwtAuthConverter));
                });
        // .addFilterAfter(customAuthorizationHeaderFilter(),
        // BearerTokenAuthenticationFilter.class);
        // .addFilterAfter(createPolicyEnforcerFilter(),
        // BearerTokenAuthenticationFilter.class);

        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.oauth2Login(login -> login
                .successHandler(keycloakLoginSuccessHandler))
                .logout(logout -> logout
                        // .logoutRequestMatcher("/logout")
                        .addLogoutHandler(keycloakLogoutHandler)
                        .logoutSuccessUrl("http://localhost:8081/"));

        return http.build();
    }

}