import { useEffect, useRef, useState } from "react";
import { AnimatedCounter } from "react-animated-counter";
import "./App.css";

import { io } from "socket.io-client";

function App() {
  const socket = useRef();

  const [authMessage, setAuthMessage] = useState("");

  const [counterValue, setCounterValue] = useState(500);

  const [backgroundColor, setBackgroundColor] = useState("red");

  // const handleClick = () => {
  //   setBackgroundColor(backgroundColor === "red" ? "lightgray" : "red");
  // };

  const handleCounterUpdate = (increment) => {
    const delta = (Math.floor(Math.random() * 100) + 1) * 0.99;
    setCounterValue(increment ? counterValue + delta : counterValue - delta);
  };
  
  useEffect(() => {
    socket.current = io("ws://localhost:9013");

    socket.current.on("connnection", () => {
      console.log("connected to server");
    });
    socket.current.on("message", (msg) => {
      setAuthMessage("Owner Authorization Code : "+msg)
      console.log("message...",msg);
    });
  }, []);

  const handleClick = () => {
    socket.current.emit("message", new Date().getTime());
  };

  return (
    <div className="App">

{/* <div className="app-container">
      <div className="header-container">
        <h1>carpay POS </h1>
        <h2></h2>
      </div>
      <div className="counter-container">
        <AnimatedCounter value={counterValue} color="green" fontSize="80px" />
      </div>
      <div className="buttons-container">
        <button onClick={() => handleCounterUpdate(true)}>START</button>
        <button
          style={{
            background: backgroundColor
          }}
          onClick={() => handleClick()}
        >
          STOP
        </button>
      </div>
    </div> */}

      <h1>CarPay POS</h1>
      <h2>{authMessage}</h2>
      <button type="button" onClick={handleClick}>
        Start
      </button>
    </div>
  );
}

export default App;
