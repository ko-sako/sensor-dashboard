import React, { useEffect, useState } from "react";
import axios from "axios";
import Speedometer from 'react-d3-speedometer';

const VoltageDisplay = () => {
    const [voltage, setTemperature] = useState(null);

    const [bgColour, setBgColour] = useState('white');
    const [fontColour, setFontColour] = useState('');

    const [Threshold, setThreshold] = useState(4.1);

    useEffect(() => {
        const fetchTemperature = async () => {
            try {
                const response = await axios.get("http://localhost:8081/api/data/");
                setTemperature(response.data.voltage);

                if (response.data.voltage > Threshold) {
                    setBgColour('rgba(255, 0, 0, 0.5');
                    setFontColour('red');
                } else if (response.data.voltage <= Threshold) {
                   setBgColour('rgba(255, 255, 255, 1');
                    setFontColour('');
                }
            } catch (e) {
                console.error("Error fetching voltage data:", e);
            }
        };

        fetchTemperature();
        const interval = setInterval(fetchTemperature, 1000); // Poll every 1 sec
        return () => clearInterval(interval); // Cleanup on unmount
    }, [Threshold]);

    return (
        <div style={{ backgroundColor: bgColour}}>
            <h1 style={{ color: fontColour }}>Arduino Voltage Monitor</h1>
            {voltage && !isNaN(voltage) && (
            <Speedometer
                maxValue={5.0}
                value= {voltage}
                currentValueText={'${value} V'}
                needleColor='red'
                textColor='black'
                ringWidth={15}
                width={300}
                height={200}
                valueTextFontSize={32}
                paddingVertical={17}
            />
            )}
            <div>
                <label>
                    Threshold:
                    <input
                        type="number"
                        value={Threshold}
                        onChange={(e) => setThreshold(Number(e.target.value))}
                    />
                </label>
            </div>
        </div>
    );
};

export default VoltageDisplay;