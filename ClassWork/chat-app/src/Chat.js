import { useContext } from "react";
import { ChatContext } from "./ChatContext";

const Chat = () => {
  const { messages } = useContext(ChatContext);

  return (
    <div className="chat-box">
      {messages.map((m, i) => (
        <div key={i} className="msg">
          {m}
        </div>
      ))}
    </div>
  );
};

export default Chat;
