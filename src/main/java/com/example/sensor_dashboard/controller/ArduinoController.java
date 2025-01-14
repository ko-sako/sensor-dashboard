package com.example.sensor_dashboard.controller;

import com.example.sensor_dashboard.service.ArduinoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArduinoController {

    @Autowired
    private ArduinoService arduinoService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // WebSocket channel to send data
    // Listen to message from the frontend (React) and trigger data retrieval.
    @MessageMapping("/sendData")
    public void sendArduinoData() {
        // Fetch data from Arduino
        String arduinoData = arduinoService.readArduinoData();
        // Broadcast data to clients
        // Broadcast the Arduino data to all connected WebSocket clients under /topic/arduino-data
        messagingTemplate.convertAndSend("/topic/arduino-data", arduinoData);
    }

    @GetMapping("/arduino-data")
    public String getArduinoData() {
        return arduinoService.readArduinoData();
    }
}
