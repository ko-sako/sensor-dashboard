import './App.css';
import React from "react";
import TemperatureDisplay from "./TemperatureDisplay";
import SecondTemperatureDisplay from "./SecondTemperatureDisplay";
import VoltageDisplay from "./VoltageDisplay";

function App() {
    const [isStoring, setIsStoring] = useState(false);

    const toggleStorage = async () => {
        const response = await fetch("/toggle-storage", { method: "POST" });
        const message = await response.text();
        alert(message);
        setIsStoring(!isStoring);
    };

    const downloadCSV = () => {
        fetch("/download-csv")
            .then((response) => {
                if (response.ok) {
                    return response.blob();
                } else {
                    alert("CSV file not found.");
                    throw new Error("File not found");
                }
            })
            .then((blob) => {
                const url = window.URL.createObjectURL(blob);
                const link = document.createElement("a");
                link.href = url;
                link.download = "";
                link.click();
            });
    };

  return (
    <div className="App">
        <button onClick={toggleStorage}>
            {isStoring ? "Stop and Save Data" : "Start Storing Data"}
        </button>
        <button onClick={downloadCSV} disabled={isStoring}>
            Download CSV
        </button>

        <TemperatureDisplay />

        <SecondTemperatureDisplay />

        <VoltageDisplay />
    </div>
  );
}

export default App;