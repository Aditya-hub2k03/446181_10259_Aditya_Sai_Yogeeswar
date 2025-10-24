import React from 'react';

const Filters = () => {
  const filterList = ["Highest Rated", "Rating 4+", "Fast Delivery", "Pure Veg", "Offers"];
  return (
    <section className="filters">
      {filterList.map((filter, index) => (
        <button key={index}>{filter}</button>
      ))}
    </section>
  );
};

export default Filters;
