import React from 'react';

const RestaurantGrid = () => {
  const restaurants = [
    { name: "Spice Garden", cuisine: "Indian", rating: 4.5, time: "30 min", img: "https://images.unsplash.com/photo-1600891964599-f61ba0e24092?auto=format&fit=crop&w=600&q=60" },
    { name: "Burger Hub", cuisine: "Fast Food", rating: 4.2, time: "25 min", img: "https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?auto=format&fit=crop&w=600&q=60" },
    { name: "Italiano", cuisine: "Italian", rating: 4.7, time: "40 min", img: "https://images.unsplash.com/photo-1513104890138-7c749659a591?auto=format&fit=crop&w=600&q=60" },
    { name: "Sushi World", cuisine: "Japanese", rating: 4.8, time: "35 min", img: "https://images.unsplash.com/photo-1604908177522-050e1ebf5a59?auto=format&fit=crop&w=600&q=60" },
  ];

  return (
    <section className="restaurant-section">
      <h2>Restaurants in "Selected City"</h2>
      <div className="restaurant-grid">
        {restaurants.map((res, index) => (
          <div className="card" key={index}>
            <img src={res.img} alt={res.name} />
            <div className="card-info">
              <h3>{res.name}</h3>
              <p>{res.cuisine} · {res.rating} ★ · {res.time}</p>
            </div>
          </div>
        ))}
      </div>
    </section>
  );
};

export default RestaurantGrid;
