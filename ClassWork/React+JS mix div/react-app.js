import React from "react";
import { createRoot } from "react-dom/client";
function HelloReact() {
    return <h1>Hello from React!</h1>;
}

// Render React app
const root = createRoot(document.getElementById('react-root'));
root.render(<HelloReact />);
