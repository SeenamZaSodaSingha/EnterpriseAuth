package com.isj.gestionmateriel.controller;


import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
@RestController
public class PrivateController {
    private int userMeunCnt = 0, userFuncCnt = 0, adminMeunCnt = 0, adminFuncCnt = 0;

    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Integer> userLanding(Principal principal){
        System.out.println("Hello user is called");
        return ResponseEntity.ok(++userMeunCnt);
    }

    @GetMapping("/user/func")
	@ResponseStatus(HttpStatus.OK)
	public int userRedirect() {
        System.out.println("User cat is called");
        return ++userFuncCnt;
	}

    @GetMapping("/admin")
    @ResponseStatus(HttpStatus.OK)
    // @CrossOrigin
    public int adminLanding(){
        System.out.println("Hello admin is called");
        return ++adminMeunCnt;
    }

    @GetMapping("/admin/func")
    @ResponseStatus(HttpStatus.OK)
    public int adminRedirect(){
        System.out.println("admin cat is called");
        return ++adminFuncCnt;
    }
}
