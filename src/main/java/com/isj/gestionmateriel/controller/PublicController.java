package com.isj.gestionmateriel.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;


@RestController
@CrossOrigin(origins = "*")
public class PublicController {
    private int meunCnt = 0, funcCnt = 0;

    @GetMapping("/authorized")
    @ResponseStatus(HttpStatus.OK)
    public String isAuthorized() {
        System.out.println("Auth check is called");
        return "";
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public int publicLanding() {
        System.out.println("Hello public is called");
        return ++meunCnt;
    }

    @GetMapping("/func")
    @ResponseStatus(HttpStatus.OK)
    public int publicFunc() {
        System.out.println("public func 1 is called");
        return ++funcCnt;
    }
}
