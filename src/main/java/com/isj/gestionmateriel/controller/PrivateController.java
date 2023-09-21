package com.isj.gestionmateriel.controller;


import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
@RestController
public class PrivateController {
    
    // @RequestMapping(method = RequestMethod.OPTIONS)
    // @ResponseStatus(HttpStatus.OK)
    // public void handle() {
    //     // This method handles OPTIONS requests with a 200 OK status code
    // }

    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    // @CrossOrigin
    public void userLanding(){
        System.out.println("Hello user is called");
        // System.out.println("Hello, "+ jwt.getClaimAsvoid("preferred_username") + "!");
        // return "This is User Portal";
    }

    @GetMapping("/user/func")
	@ResponseStatus(HttpStatus.OK)
    // @CrossOrigin
	public void userRedirect(/*@PathVariable("path") void path*/) {
        System.out.println("User cat is called");
		// System.out.println("Hello, "+ jwt.getClaimAsvoid("preferred_username") + "!");
        // return "This is User Func";
	}

    @GetMapping("/admin")
    @ResponseStatus(HttpStatus.OK)
    // @CrossOrigin
    public void adminLanding(){
        System.out.println("Hello admin is called");
        // System.out.println("Hello, "+ jwt.getClaimAsvoid("preferred_username") + "!");
        // return "This is Admin Portal";
    }

    @GetMapping("/admin/func")
    @ResponseStatus(HttpStatus.OK)
    // @CrossOrigin
    public void adminRedirect(/*@PathVariable("path") void path*/ ){
        System.out.println("admin cat is called");
        // System.out.println("Hello, "+ jwt.getClaimAsvoid("preferred_username") + "!");
        // return "This is Admin Func";
    }
}
