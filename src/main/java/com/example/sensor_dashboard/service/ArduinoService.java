package com.example.sensor_dashboard.service;

import com.fazecast.jSerialComm.SerialPort;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ArduinoService {

    private static final String ARDUINO_PORT = "COM10";
    private SerialPort serialPort;
    private String lastTemperature = "";
    private String lastVoltage = "";

    public String getLastVoltage() {
        return lastVoltage;
    }

    public void setLastVoltage(String lastVoltage) {
        this.lastVoltage = lastVoltage;
    }

    public void setSerialPort(SerialPort serialPort) {
        this.serialPort = serialPort;
    }

    public Map<String, String> getLastData() {
        Map<String, String> data = new HashMap<>();
        data.put("temperature", lastTemperature);
        data.put("voltage", lastVoltage);
        return data;
    }

    @PostConstruct
    public void initialize() {
        System.out.println("Initializing Arduino Service...");
        serialPort = SerialPort.getCommPort(ARDUINO_PORT);
        serialPort.setBaudRate(9600);

        // Set the timeout for reading from the serial port
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 1000, 0);  // 1 seconds timeout

        if (serialPort.openPort()) {
            System.out.println("Port opened successfully.");
            startReadingData();
        } else {
            System.out.println("Failed to open the port.");
        }
    }

    public String getLastTemperature() {
        return lastTemperature;
    }

    public void setLastTemperature(String lastTemperature) {
        this.lastTemperature = lastTemperature;
    }

    private void startReadingData() {
        new Thread(() -> {
            InputStream inputStream = serialPort.getInputStream();
            byte[] readBuffer = new byte[1024];
            int numBytes;
            try {
                while (true) {
                    // Read data only there are data
                    numBytes = inputStream.read(readBuffer);
                    if (numBytes > 0) {
                        String data = new String(readBuffer, 0, numBytes);
                        System.out.println("Received from Arduino: " + data);

                        String[] data_parts = data.split("\\s+|=|(?<=\\d)(?=[A-Za-z])");

                        // Remove Empty Part
                        List<String> filteredParts = Arrays.stream(data_parts)
                                .filter(data_part -> !data_part.isEmpty())
                                .toList();

                        for (String part : filteredParts) {
                            System.out.println("part: " + part);
                        }

                        // Get Temperature
                        // String temperature = data.split(": ")[1].split(" ")[0];
                        String temperature = filteredParts.get(1);
                        setLastTemperature(temperature);
                        System.out.println("Temperature: " + temperature);

                        // Get Voltage
                        String voltage = filteredParts.get(4);
                        setLastVoltage(voltage);
                        System.out.println("Voltage: " + voltage);

                    } else {
                        // Process if there were no data (log)
                        System.out.println("No data received. Waiting for next read...");
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading Arduino data: " + e.getMessage());
            }
        }).start();
    }
}
