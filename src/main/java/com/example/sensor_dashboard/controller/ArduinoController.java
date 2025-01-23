package com.example.sensor_dashboard.controller;

import com.example.sensor_dashboard.service.ArduinoService;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

    @GetMapping("/api/toggle-storage")
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

    @GetMapping("/api/download-csv")
    public ResponseEntity<Resource> downloadCSV() throws IOException {
        File file = new File("./sensor_data.csv");
        if (!file.exists()) {
            System.out.println("File not found...");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Wrap the file input stream into InputStreamResource
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=sensor_data.csv") // Suggest filename
                .contentType(MediaType.parseMediaType("text/csv")) // Content-Type: text/csv
                .contentLength(file.length()) // Set the content length
                .body(resource); // Return the InputStreamResource
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
