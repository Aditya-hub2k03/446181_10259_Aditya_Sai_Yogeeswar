import { ChatProvider } from "./ChatContext";
import Chat from "./Chat";
import MessageInput from "./MessageInput";
import "./styles.css";

export default function App() {
  return (
    <ChatProvider>
      <div className="container">
        <h2>Real Time Chat</h2>
        <Chat />
        <MessageInput />
      </div>
    </ChatProvider>
  );
}
