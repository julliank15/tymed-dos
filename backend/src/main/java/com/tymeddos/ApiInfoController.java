package com.tymeddos;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiInfoController {

    @GetMapping("/")
    public Map<String, String> root() {
        return Map.of(
                "message", "Welcome to Tymed-Dos API",
                "docs", "/docs",
                "version", "0.1.0"
        );
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "healthy");
    }
}
