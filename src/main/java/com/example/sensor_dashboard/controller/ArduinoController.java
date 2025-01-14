package com.example.sensor_dashboard.controller;

import com.example.sensor_dashboard.service.ArduinoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArduinoController {

    private final ArduinoService arduinoService;

    @Autowired
    public ArduinoController(ArduinoService arduinoService) {
        this.arduinoService = arduinoService;
    }

    @GetMapping("/api/temperature")
    public TemperatureResponse getTemperature() {
        return new TemperatureResponse(arduinoService.getLastTemperature());
    }

    public static class TemperatureResponse {
        private String temperature;

        public TemperatureResponse(String temperature) {
            this.temperature = temperature;
        }

        public String getTemperature() {
            return temperature;
        }
    }
}
