package com.isj.gestionmateriel.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

// @RequestMapping("/api/v1")
@Controller
public class PrivateController {
    @GetMapping("/user")
    // @PreAuthorize("hasRole('User')")
    public String userLanding(@AuthenticationPrincipal Jwt jwt){
        System.out.println("Hello user is called");
        System.out.println("Hello, "+ jwt.getClaimAsString("preferred_username") + "!");
        return "user/user_portal";
    }

    @GetMapping("/user/func_1")
	// @PreAuthorize("hasRole('User')")
	public String userRedirect(/*@PathVariable("path") String path*/@AuthenticationPrincipal Jwt jwt) {
        System.out.println("User cat is called");
		System.out.println("Hello, "+ jwt.getClaimAsString("preferred_username") + "!");
        return "user/user_func_1";
	}

    @GetMapping("/admin")
    // @PreAuthorize("hasRole('Admin')")
    public String adminLanding(@AuthenticationPrincipal Jwt jwt){
        System.out.println("Hello admin is called");
        // System.out.println("Hello, "+ jwt.getClaimAsString("preferred_username") + "!");
        return "admin/admin_portal";
    }

    @GetMapping("/admin/func_1")
    // @PreAuthorize("hasRole('Admin')")
    public String adminRedirect(/*@PathVariable("path") String path*/ @AuthenticationPrincipal Jwt jwt){
        System.out.println("admin cat is called");
        // System.out.println("Hello, "+ jwt.getClaimAsString("preferred_username") + "!");
        return "admin/admin_func_1";
    }
}
