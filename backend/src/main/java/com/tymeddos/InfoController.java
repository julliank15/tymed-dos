package com.tymeddos;

import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    private final String name;

    public InfoController(@Value("${spring.application.name}") String name) {
        this.name = name;
    }

    @GetMapping("/")
    public Map<String, String> info() {
        return Map.of(
                "name", name,
                "status", "running",
                "health", "/actuator/health");
    }
}
