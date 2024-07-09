package com.template.project.application;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class HealthCheckGateway {

    @GetMapping("/health-check")
    public ResponseEntity healthCheck() {
        return ResponseEntity.ok().build();
    }
}
