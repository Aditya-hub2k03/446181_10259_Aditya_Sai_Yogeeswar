import { useState, useContext } from "react";
import { ChatContext } from "./ChatContext";

const MessageInput = () => {
  const [text, setText] = useState("");
  const { sendMessage } = useContext(ChatContext);

  const handleSend = () => {
    if (text.trim().length === 0) return;
    sendMessage(text);
    setText("");
  };

  return (
    <div className="input-container">
      <input
        value={text}
        onChange={(e) => setText(e.target.value)}
        placeholder="Say something..."
      />
      <button onClick={handleSend}>Send</button>
    </div>
  );
};

export default MessageInput;
