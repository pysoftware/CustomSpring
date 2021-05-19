package com.sazonov.controllers;

import com.sazonov.ioc.Controller;
import com.sazonov.ioc.web.requestMapping.PostMapping;
import lombok.extern.slf4j.Slf4j;

@Controller(uriPath = "/auth")
@Slf4j
public class AuthController {

    @PostMapping(name = "/login")
    public void login() {

    }

    @PostMapping(name = "/logout")
    public void logout() {


    }
}
