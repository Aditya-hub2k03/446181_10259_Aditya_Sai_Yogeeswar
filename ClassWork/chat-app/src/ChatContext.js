import { createContext, useState, useEffect } from "react";
import { socket } from "./socket";

export const ChatContext = createContext();

export const ChatProvider = ({ children }) => {
  const [messages, setMessages] = useState([]);

  useEffect(() => {
    socket.on("chat", (msg) => {
      setMessages((prev) => [...prev, msg]);
    });
    return () => socket.off("chat");
  }, []);

  const sendMessage = (msg) => {
    socket.emit("chat", msg);
  };

  return (
    <ChatContext.Provider value={{ messages, sendMessage }}>
      {children}
    </ChatContext.Provider>
  );
};
