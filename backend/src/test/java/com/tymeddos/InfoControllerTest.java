package com.tymeddos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class InfoControllerTest {

    @Test
    void infoReportsName() {
        var body = new InfoController("tymed-dos-backend").info();
        assertEquals("tymed-dos-backend", body.get("name"));
        assertEquals("/actuator/health", body.get("health"));
    }
}
