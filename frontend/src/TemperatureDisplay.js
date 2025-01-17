import React, { useEffect, useState } from "react";
import axios from "axios";
import Speedometer from 'react-d3-speedometer';

const TemperatureDisplay = () => {
    const [temperature, setTemperature] = useState(null);

    useEffect(() => {
        const fetchTemperature = async () => {
            try {
                const response = await axios.get("http://localhost:8081/api/temperature");
                setTemperature(response.data.temperature);
            } catch (e) {
                console.error("Error fetching temperature data:", e);
            }
        };

        fetchTemperature();
        const interval = setInterval(fetchTemperature, 1000); // Poll every 1 sec
        return () => clearInterval(interval); // Cleanup on unmount
    }, []);

    return (
        <div>
            <h1> Temperature: {temperature ? `${temperature} C` : "Loading..."}</h1>
            {temperature && !isNaN(temperature) && (
            <Speedometer
                maxValue={100}
                value= {temperature}
                currentValueText={'${value} C'}
                needleColor="red"
                ringWidth={15}
                width={300}
                height={200}
                valueTextFontSize={32}
                paddingVertical={17}
            />
            )}
        </div>
    );
};

export default TemperatureDisplay;