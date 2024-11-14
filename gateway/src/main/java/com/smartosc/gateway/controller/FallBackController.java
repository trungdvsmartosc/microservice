package com.smartosc.gateway.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallBackController {

    @Value("${fall-back-message}")
    private String fallBackMessage;

    @GetMapping("/user-service")
    public String userServiceFallBack() {
        return fallBackMessage;
    }
}