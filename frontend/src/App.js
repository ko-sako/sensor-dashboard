import './App.css';
import React from "react";
import TemperatureDisplay from "./TemperatureDisplay";
import SecondTemperatureDisplay from "./SecondTemperatureDisplay";
import VoltageDisplay from "./VoltageDisplay";

function App() {
  return (
    <div className="App">
        <h1>Arduino Temperature Monitor</h1>
        <TemperatureDisplay />

        <h1>Arduino Second Temperature Monitor</h1>
        <SecondTemperatureDisplay />

        <h1>Arduino Voltage Monitor</h1>
        <VoltageDisplay />
    </div>
  );
}

export default App;