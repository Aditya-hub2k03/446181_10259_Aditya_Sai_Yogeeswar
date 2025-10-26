import React from "react";

const Shimmer = () => (
  <div className="shimmer-grid">
    {Array.from({ length: 12 }).map((_, i) => (
      <div className="shimmer-card" key={i}></div>
    ))}
  </div>
);

export default Shimmer;
