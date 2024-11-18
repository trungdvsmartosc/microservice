package com.smartosc.userservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class DemoController {

    private static final Map<String, String> users = Map.of(
            "1", "User One 9001"
    );

    @GetMapping("/{id}")
    public ResponseEntity<String> getUserById(@PathVariable String id) {
        String user = users.get(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        return ResponseEntity.ok(user);
    }
}
