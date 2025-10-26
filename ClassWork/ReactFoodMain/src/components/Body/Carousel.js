import React, { useRef } from "react";

const items = [
  "Pizza",
  "Burger",
  "Biryani",
  "Pasta",
  "Cake",
  "Momoz",
  "Ice Cream",
  "Dosa",
  "Sandwich",
  "Milkshake",
  "French Fries",
  "Salad",
];

const Carousel = () => {
  const carouselRef = useRef(null);

  const scroll = (direction) => {
    if (direction === "left")
      carouselRef.current.scrollBy({ left: -200, behavior: "smooth" });
    else carouselRef.current.scrollBy({ left: 200, behavior: "smooth" });
  };

  return (
    <div className="carousel-container">
      <button className="carousel-btn left" onClick={() => scroll("left")}>
        &#10094;
      </button>
      <div className="carousel" ref={carouselRef}>
        {items.map((item, i) => (
          <div className="food-card" key={item + "-" + i}>
            {item}
          </div>
        ))}
      </div>
      <button className="carousel-btn right" onClick={() => scroll("right")}>
        &#10095;
      </button>
    </div>
  );
};

export default Carousel;
