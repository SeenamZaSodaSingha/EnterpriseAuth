package com.isj.gestionmateriel.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1")
@Controller
public class PrivateController {
    @GetMapping("/user")
    // @PreAuthorize("hasRole('client_user')")
    public String userLanding(){
        System.out.println("Hello user is called");
        // System.out.println("Hello, "+ jwt.getClaimAsString("preferred_username") + "!");
        return "user/user_portal";
    }

    @GetMapping("/user/{path}")
	// @PreAuthorize("hasRole('client_user')")
	public String userRedirect(@PathVariable("path") String path) {
        System.out.println("User cat is called");
		// System.out.println("Hello, "+ jwt.getClaimAsString("preferred_username") + "!");
        return "user/user_"+path;
	}

    @GetMapping("/admin")
    // @PreAuthorize("hasRole('client_admin')")
    public String adminLanding(){
        System.out.println("Hello admin is called");
        // System.out.println("Hello, "+ jwt.getClaimAsString("preferred_username") + "!");
        return "admin/admin_portal";
    }

    @GetMapping("/admin/{path}")
    // @PreAuthorize("hasRole('client_admin')")
    public String adminRedirect(@PathVariable("path") String path){
        System.out.println("admin cat is called");
        // System.out.println("Hello, "+ jwt.getClaimAsString("preferred_username") + "!");
        return "admin/admin_"+path;
    }
}
