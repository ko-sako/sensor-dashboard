package com.example.sensor_dashboard.controller;

import com.example.sensor_dashboard.service.ArduinoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class ArduinoController {

    private final ArduinoService arduinoService;

    @Autowired
    public ArduinoController(ArduinoService arduinoService) {
        this.arduinoService = arduinoService;
    }

//    @GetMapping("/api/temperature/{arduinoPort}")
//    public TemperatureResponse getTemperature(@PathVariable String arduinoPort) {
//        TemperatureResponse temp_r = new TemperatureResponse(arduinoService.getLastTemperature(arduinoPort));
//        // System.out.println("aaa!!!" + temp_r);
//        return new TemperatureResponse(arduinoService.getLastTemperature());
//    }

    @GetMapping("/api/temperature/")
    public TemperatureResponse getTemperature() {
        return new TemperatureResponse(arduinoService.getLastTemperature());
    }

    @GetMapping("/api/data/")
    public Map getAllData() {
        return arduinoService.getLastData();
    }

    @GetMapping("/api/data-storage")
    public ResponseEntity<String> dataStorage() {
        if (!arduinoService.isStoringData()) {
            // Start Data Storing
            arduinoService.startSavingData();
            arduinoService.toggleDataStorage();
            return ResponseEntity.ok("Data storage started.");
        } else {
            arduinoService.saveBufferToCSV();
            arduinoService.toggleDataStorage();
            return ResponseEntity.ok("Data storage stopped and saved to CSV");
        }
    }


    public static class TemperatureResponse {
        private final String temperature;

        public TemperatureResponse(String temperature) {
            this.temperature = temperature;
        }

        public String getTemperature() {
            return temperature;
        }
    }
}
