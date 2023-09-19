package com.isj.gestionmateriel.controller;
// import com.isj.gestionmateriel.config.KeycloakLogoutHandler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import static org.springframework.http.HttpMethod.GET;
import org.springframework.http.HttpStatus;


@RestController
@CrossOrigin(origins = "*")
public class PublicController {

    private final RestTemplate restTemplate = new RestTemplateBuilder().build();


    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public void publicLanding() {
        System.out.println("Hello public is called");
        // return "Hello public is called";
    }

    // @GetMapping("/login")
    // public String login() {
    //     System.out.println("Login is called");
    //     return "/";
    // }


    @GetMapping("/func")
    @ResponseStatus(HttpStatus.OK)
    public void  publicFunc() {
        System.out.println("public func 1 is called");
        // return "Public func is called";
    }

    // @GetMapping("/200")
    // public String ok() {
    //     System.out.println("200 OK is called");
    //     return "status/200";
    // }

    // @GetMapping("/401")
    // public String unAuth() {
    //     System.out.println("401 Unauth is called");
    //     return "status/401";
    // }

    // @GetMapping("/404")
    // public String notFound() {
    //     System.out.println("404 Not found is called");
    //     return "status/404";
    // }

    // @GetMapping("/500")
    // public String internalServerErr() {
    //     System.out.println("500 Internal Server Error is called");
    //     return "status/500";
    // }
}
