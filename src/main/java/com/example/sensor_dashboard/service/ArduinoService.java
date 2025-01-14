package com.example.sensor_dashboard.service;

import com.fazecast.jSerialComm.SerialPort;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.IOException;

@Service
public class ArduinoService {

    private static final String ARDUINO_PORT = "COM9";
    private SerialPort serialPort;
    private String lastTemperature = "";

    @PostConstruct
    public void initialize() {
        System.out.println("Initializing Arduino Service...");
        serialPort = SerialPort.getCommPort(ARDUINO_PORT);
        serialPort.setBaudRate(9600);

        // Set the timeout for reading from the serial port
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 10000, 0);  // 10 seconds timeout

        if (serialPort.openPort()) {
            System.out.println("Port opened successfully.");
            startReadingData();
        } else {
            System.out.println("Failed to open the port.");
        }
    }

    private void startReadingData() {
        new Thread(() -> {
            InputStream inputStream = serialPort.getInputStream();
            byte[] readBuffer = new byte[1024];
            int numBytes;
            try {
                while (true) {
                    // データがある場合のみ読み取る
                    numBytes = inputStream.read(readBuffer);
                    if (numBytes > 0) {
                        String data = new String(readBuffer, 0, numBytes);
                        if (data.contains("Temperature:")) {
                            System.out.println("Received from Arduino: " + data);
                            String temperature = data.split(":")[1].split(" ")[0];
                            setLastTemperature(temperature);
                        }
                    } else {
                        // データがない場合の処理（ログ出力）
                        System.out.println("No data received. Waiting for next read...");
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading Arduino data: " + e.getMessage());
            }
        }).start();
    }

    public String getTemperature() {
        return lastTemperature;
    }

    private void setLastTemperature(String temperature) {
        this.lastTemperature = temperature;
    }
}
