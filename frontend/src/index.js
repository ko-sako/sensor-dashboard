import React, { useEffect, useState } from 'react';
import ReactDOM from 'react-dom';
import { Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

function App() {
  const [sensorData, setSensorData] = useState(null);

  useEffect(() => {
    // Establish a connection with the WebSocket server
    const socket = new SockJS('http://localhost:8080/ws');
    const client = Stomp.over(socket);

    client.connect({}, (frame) => {
      console.log('Connected: ' + frame);

      // Subscribe to the topic that the backend will broadcast to
      client.subscribe('/topic/arduino-data', (message) => {
        console.log('Received data:', message.body);
        setSensorData(message.body); // Update the sensor data state
      });

      // Optionally, send a message to the backend to request data
      client.send('/app/sendData', {}, {});
    });

    // Clean up the WebSocket connection when the component unmounts
    return () => {
      if (client) {
        client.disconnect();
      }
    };
  }, []); // Empty dependency array to ensure this effect runs only once on mount

  return (
    <div>
      <h1>Arduino Sensor Data</h1>
      <p>Check the data received from Arduino:</p>
      {sensorData ? (
        <p>{sensorData}</p>
      ) : (
        <p>No data received yet.</p>
      )}
    </div>
  );
}

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
