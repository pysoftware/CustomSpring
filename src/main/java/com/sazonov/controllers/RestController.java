package com.sazonov.controllers;


import com.sazonov.ioc.Controller;
import com.sazonov.ioc.web.requestMapping.GetMapping;
import com.sazonov.ioc.web.requestMapping.PostMapping;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Controller(uriPath = "/hello")
@Slf4j
public class RestController {

    @GetMapping(name = "/test")
    public String handleGetMethod() {
        return "";
    }

    @PostMapping(name = "/test")
    public String handlePostMethod() {
        return "";
    }

    public static void main(String[] args) {
        Arrays.stream(RestController.class.getAnnotations()).forEach(item -> log.info(String.valueOf(item)));
    }
}
