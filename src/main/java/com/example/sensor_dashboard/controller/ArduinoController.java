package com.example.sensor_dashboard.controller;

import com.example.sensor_dashboard.service.ArduinoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArduinoController {

    @Autowired
    private ArduinoService arduinoService;

    @GetMapping("/arduino-data")
    public String getArduinoData() {
        return arduinoService.readArduinoData();
    }
}
