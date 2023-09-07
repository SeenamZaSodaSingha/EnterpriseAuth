package com.isj.gestionmateriel.controller;
import com.isj.gestionmateriel.config.KeycloakLogoutHandler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@Controller
public class PublicController {

    @GetMapping("/")
    public String publicLanding(@AuthenticationPrincipal Jwt jwt) {
        System.out.println("Hello public is called");
        return "public/public_portal";
    }

    @GetMapping("/login")
    public String login() {
        System.out.println("Login is called");
        return "/";
    }


    @GetMapping("/func_1")
    public String publicFunc(/*@PathVariable("path") String path*/@AuthenticationPrincipal Jwt jwt) {
        System.out.println("public func 1 is called");
        return "public/public_func_1";
    }

    @GetMapping("/200")
    public String ok() {
        System.out.println("200 OK is called");
        return "status/200";
    }

    @GetMapping("/401")
    public String unAuth() {
        System.out.println("401 Unauth is called");
        return "status/401";
    }

    @GetMapping("/404")
    public String notFound() {
        System.out.println("404 Not found is called");
        return "status/404";
    }

    @GetMapping("/500")
    public String internalServerErr() {
        System.out.println("500 Internal Server Error is called");
        return "status/500";
    }
}
