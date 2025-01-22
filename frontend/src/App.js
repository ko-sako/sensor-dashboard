import './App.css';
import React from "react";
import TemperatureDisplay from "./TemperatureDisplay";
import SecondTemperatureDisplay from "./SecondTemperatureDisplay";
import VoltageDisplay from "./VoltageDisplay";

function App() {
  return (
    <div className="App">
        <TemperatureDisplay />

        <SecondTemperatureDisplay />

        <VoltageDisplay />
    </div>
  );
}

export default App;