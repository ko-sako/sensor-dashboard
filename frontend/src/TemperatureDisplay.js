import React, { useEffect, useState } from "react";
import axios from "axios";
import Speedometer from 'react-d3-speedometer';

const TemperatureDisplay = () => {
    const [temperature, setTemperature] = useState(null);
    const [bgColour, setBgColour] = useState('white');
    const [flashing, setFlashing] = useState(false);

    useEffect(() => {
        const fetchTemperature = async () => {
            try {
                const response = await axios.get("http://localhost:8081/api/temperature/");
                setTemperature(response.data.temperature);


                console.log(response.data.temperature);

                const TEMP = 22;
                if (response.data.temperature > TEMP) {
                    console.log("temperature red");
                    setBgColour('rgba(255, 0, 0, 0.5');
                    setFlashing(true);
                } else if (response.data.temperature <= TEMP) {
                   console.log("temperature white");
                   setBgColour('rgba(255, 255, 255, 1');
                   setFlashing(false);
                }

            } catch (e) {
                console.error("Error fetching temperature data:", e);
            }
        };


        fetchTemperature();
        const interval = setInterval(fetchTemperature, 1000); // Poll every 1 sec
        return () => clearInterval(interval); // Cleanup on unmount
    }, []);

    return (
        <div style={{ backgroundColor: bgColour}}>
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