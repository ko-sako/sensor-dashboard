import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';

const App = () => {
  return <h1>Hello, React!</h1>;
};

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(<App />);
