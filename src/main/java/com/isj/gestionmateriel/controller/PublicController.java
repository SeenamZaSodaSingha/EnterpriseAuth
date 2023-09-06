package com.isj.gestionmateriel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

@Controller
public class PublicController {

    @GetMapping("/")
    public String publicLanding() {
        System.out.println("Hello public is called");
        return "public/public_portal";
    }

    @GetMapping("/login")
    public String login() {
        System.out.println("Login is called");
        return "status/500";
    }

    @GetMapping("/{path}")
    public String publicFunc(@PathVariable("path") String path) {
        System.out.println("public " + path + " is called");
        return "public/public_" + path;
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
