import React from "react";
import ComponentA from "./ComponentA";
import { createRoot } from "react-dom/client";

const App = () => {
    return(<div>
        <ComponentA />
        </div>);
}
var reactElement = document.getElementById("root");
var reactRoot = createRoot(reactElement);
reactRoot.render(<App />);
