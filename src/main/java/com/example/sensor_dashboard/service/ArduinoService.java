package com.example.sensor_dashboard.service;

import com.fazecast.jSerialComm.SerialPort;
import org.springframework.stereotype.Service;

@Service
public class ArduinoService {
    private SerialPort comPort;

    // Constructor of the ArduinoService class, is called when an instance of the ArduinoService class is created
    public ArduinoService() {
        comPort = SerialPort.getCommPort("COM9");
        comPort.setBaudRate(9600);
    }

    public String readArduinoData() {
        String data = "";

        // Open the serial port
        if (comPort.openPort()) {
            System.out.println("Port opened successfully.");
            try {

                // Prepare a buffer to read data
                byte[] readBuffer = new byte[1024];
                // Read bytes from the serial port
                int numBytes = comPort.readBytes(readBuffer, readBuffer.length);
                // Process the received data
                data = new String(readBuffer, 0, numBytes).trim(); // Clean data
            } catch (Exception e) {
                // Handle any exceptions (e.g., port not found, IO error)
                System.err.println("Error while reading from serial port: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to open the port.");
        }
        return data;
    }
}
