import React, { useEffect, useState } from "react";
import axios from "axios";
import Speedometer from 'react-d3-speedometer';

const VoltageDisplay = () => {
    const [voltage, setTemperature] = useState(null);

    useEffect(() => {
        const fetchTemperature = async () => {
            try {
                const response = await axios.get("http://localhost:8081/api/data");
                setTemperature(response.data.voltage);
            } catch (e) {
                console.error("Error fetching voltage data:", e);
            }
        };

        fetchTemperature();
        const interval = setInterval(fetchTemperature, 1000); // Poll every 1 sec
        return () => clearInterval(interval); // Cleanup on unmount
    }, []);

    return (
        <div>
            <Speedometer
                maxValue={100}
                value= {voltage}
                currentValueText={'${value} C'}
                needleColor="red"
                ringWidth={15}
                width={300}
                height={200}
                valueTextFontSize={32}
                paddingVertical={17}
            />
        </div>
    );
};

export default VoltageDisplay;