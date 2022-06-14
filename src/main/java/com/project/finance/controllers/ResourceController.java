package com.project.finance.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {
    @GetMapping("/protect")
    public ResponseEntity<?> allAccess() {
        return ResponseEntity.ok("success");
    }
}
