import './App.css';
import React, { useState } from "react";
import TemperatureDisplay from "./TemperatureDisplay";
import SecondTemperatureDisplay from "./SecondTemperatureDisplay";
import VoltageDisplay from "./VoltageDisplay";

function App() {
    const [isStoring, setIsStoring] = useState(false);
    const [isDisable, setIsDisable] = useState(true);

    const toggleStorage = async () => {
        const response = await fetch("http://localhost:8081/api/toggle-storage", { method: "GET" });
        const message = await response.text();
        setIsStoring(!isStoring);
        setIsDisable(!isStoring);
    };

 const downloadCSV = () => {
   fetch("http://localhost:8081/api/download-csv")
     .then((response) => {
       if (response.ok) {
         return response.blob(); // Ensure you process the response as a binary blob
       } else {
         alert("CSV file not found!");
         throw new Error("File not found");
       }
     })
     .then((blob) => {
       // Create a URL for the Blob and trigger download
       const url = window.URL.createObjectURL(blob);
       const link = document.createElement("a");
       link.href = url;
       link.download = "sensor_data.csv"; // Default filename, can be customized
       link.click();
     })
     .catch((error) => {
       console.error("Error downloading CSV:", error);
     });
 };

  return (
    <div className="App">
        <button onClick={toggleStorage}>
            {isStoring ? "Stop and Save Data" : "Start Storing Data"}
        </button>
        <button onClick={downloadCSV} disabled={isDisable}>
            Download CSV
        </button>

        <TemperatureDisplay />

        <SecondTemperatureDisplay />

        <VoltageDisplay />
    </div>
  );
}

export default App;