package com.example.sensor_dashboard.controller;

import com.example.sensor_dashboard.service.ArduinoService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class ArduinoController {

    private final ArduinoService arduinoService;

    @Autowired
    public ArduinoController(ArduinoService arduinoService) {
        this.arduinoService = arduinoService;
    }

    @GetMapping("/api/temperature")
    public TemperatureResponse getTemperature() {
        TemperatureResponse temp_r = new TemperatureResponse(arduinoService.getLastTemperature());
        System.out.println("aaa!!!" + temp_r);
        return new TemperatureResponse(arduinoService.getLastTemperature());
    }

    @Getter
    public static class TemperatureResponse {
        private final String temperature;

        public TemperatureResponse(String temperature) {
            this.temperature = temperature;
        }

    }
}
