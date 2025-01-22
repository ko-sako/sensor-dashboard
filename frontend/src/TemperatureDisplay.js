import React, { useEffect, useState } from "react";
import axios from "axios";
import Speedometer from 'react-d3-speedometer';

const TemperatureDisplay = () => {
    const [temperature, setTemperature] = useState(null);

    const [bgColour, setBgColour] = useState('white');
    const [fontColour, setFontColour] = useState('');

    const [Threshold, setThreshold] = useState(22);

    useEffect(() => {
        const fetchTemperature = async () => {
            try {
                const response = await axios.get("http://localhost:8081/api/temperature/");
                setTemperature(response.data.temperature);

                if (response.data.temperature > Threshold) {
                    setBgColour('rgba(255, 0, 0, 0.5');
                    setFontColour('red');
                } else if (response.data.temperature <= Threshold) {
                   setBgColour('rgba(255, 255, 255, 1');
                   setFontColour('');
                }

            } catch (e) {
                console.error("Error fetching temperature data:", e);
            }
        };

        fetchTemperature();
        const interval = setInterval(fetchTemperature, 1000); // Poll every 1 sec
        return () => clearInterval(interval); // Cleanup on unmount
    }, [Threshold]);

    return (
        <div style={{ backgroundColor: bgColour }}>
            <h1 style={{ color: fontColour }}>Arduino Temperature Monitor</h1>
            {temperature && !isNaN(temperature) && (
            <Speedometer
                maxValue={100}
                value= {temperature}
                currentValueText={'${value} C'}
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

export default TemperatureDisplay;