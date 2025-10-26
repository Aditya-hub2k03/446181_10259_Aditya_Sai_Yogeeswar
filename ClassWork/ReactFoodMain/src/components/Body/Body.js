import React from "react";
import RestaurantGrid from "./RestaurantGrid.js";

const Body = ({ city, lat, lng, searchText, triggerSearch }) => {
  // Carousel placeholder items
  const foods = [
    "Pizza",
    "Burger",
    "Sushi",
    "Pasta",
    "Biryani",
    "Dosa",
    "Tacos",
    "Salad",
  ];

  return (
    <div className="body-content">
      <div className="carousel-container">
        <button className="carousel-btn">◀</button>
        <div className="carousel">
          {foods.map((food, i) => (
            <div className="food-card" key={i}>
              {food}
            </div>
          ))}
        </div>
        <button className="carousel-btn">▶</button>
      </div>

      <RestaurantGrid
        city={city}
        lat={lat}
        lng={lng}
        searchText={searchText}
        triggerSearch={triggerSearch}
      />
    </div>
  );
};

export default Body;
