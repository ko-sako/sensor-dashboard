package com.example.sensor_dashboard.service;

import com.fazecast.jSerialComm.SerialPort;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.InputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class ArduinoService {

    private static final String ARDUINO_PORT = "COM11";
    private SerialPort serialPort;
    private String lastTemperature = "";
    private String lastVoltage = "";
    private String lastSecondTemperature = "";

    private boolean storingData = false;
    private List<Map<String, String>> dataBuffer = new CopyOnWriteArrayList<>();

    public String getLastSecondTemperature() {
        return lastSecondTemperature;
    }

    public void setLastSecondTemperature(String lastSecondTemperature) {
        this.lastSecondTemperature = lastSecondTemperature;
    }

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
        data.put("temperature_2", lastSecondTemperature);
        return data;
    }

    public boolean isStoringData() {
        return storingData;
    }

    public void toggleDataStorage() {
        if(storingData) {
            System.out.println("Storing Data Status: " + storingData);
        } else {
            System.out.println("Storing Data Status: " + storingData);
        }
        storingData = !storingData;
        System.out.println("[NEW] Storing Data Status: " + storingData);
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
//                        System.out.println("Received from Arduino: " + data);

                        String[] data_parts = data.split("\\s+|=|(?<=\\d)(?=[A-Za-z])");

                        // Remove Empty Part
                        List<String> filteredParts = Arrays.stream(data_parts)
                                .filter(data_part -> !data_part.isEmpty())
                                .toList();

//                        for (String part : filteredParts) {
//                            System.out.println("part: " + part);
//                        }

                        // Get Temperature
                        // String temperature = data.split(": ")[1].split(" ")[0];
                        String temperature = filteredParts.get(1);
                        setLastTemperature(temperature);
                        // System.out.println("Temperature: " + temperature);

                        // Get Second Temperature
                        String temperature_2 = filteredParts.get(4);
                        setLastSecondTemperature(temperature_2);
                        // System.out.println("Second Temperature: " + temperature_2);

                        // Get Voltage
                        String voltage = filteredParts.get(7);
                        setLastVoltage(voltage);
                        //System.out.println("Voltage: " + voltage);

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

    public void startSavingData() {
        new Thread(() -> {
            while(storingData){
                Map<String, String> data = getLastData();
                dataBuffer.add(new HashMap<>(data));
                try{
                    Thread.sleep(1000); // 1 sec delay
                } catch(InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    public void saveBufferToCSV() {
        try(FileWriter csvWriter = new FileWriter("sensor_data.csv")) {
            csvWriter.append("Date, Time, TemperatureNo1, TemperatureNo2, Voltage\n");
            for(Map<String, String>rowData:dataBuffer) {
                csvWriter.append(String.join(",",rowData.values())).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
