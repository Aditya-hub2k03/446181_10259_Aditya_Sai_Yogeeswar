import React, { useState } from "react";

const cities = [
  { name: "Hyderabad", lat: 17.3843, lng: 78.4583 },
  { name: "Bangalore", lat: 12.9716, lng: 77.5946 },
  { name: "Chennai", lat: 13.0827, lng: 80.2707 },
  { name: "Mumbai", lat: 19.076, lng: 72.8777 },
  { name: "Visakhapatnam", lat: 17.735462, lng: 83.3275007 },
];

const Header = ({
  city,
  onCityChange,
  searchText,
  setSearchText,
  setTriggerSearch,
}) => {
  const [darkMode, setDarkMode] = useState(false);

  const handleCitySelect = (e) => {
    const selectedCity = cities.find((c) => c.name === e.target.value);
    if (selectedCity) {
      onCityChange(selectedCity.lat, selectedCity.lng, selectedCity.name);
    }
  };

  const handleSearch = () => setTriggerSearch((prev) => !prev);

  const toggleDarkMode = () => {
    setDarkMode(!darkMode);
    document.body.classList.toggle("dark", !darkMode);
  };

  return (
    <div className="header">
      <div className="logo-section">
        <img src="https://i.ibb.co/3cLJ7Kz/logo.png" className="logo" />
        <h2>Food Delivery</h2>
        <select
          className="location-select"
          value={city}
          onChange={handleCitySelect}
        >
          {cities.map((c) => (
            <option key={c.name}>{c.name}</option>
          ))}
        </select>
      </div>

      <div className="header-right">
        <input
          type="text"
          placeholder="Search food/restaurants..."
          value={searchText}
          onChange={(e) => setSearchText(e.target.value)}
        />
        <button onClick={handleSearch} className="header-btn">
          Search
        </button>
        <button className="header-btn signup">Signup</button>
        <label className="theme-switch">
          <input type="checkbox" onChange={toggleDarkMode} />
          <span className="slider"></span>
        </label>
      </div>
    </div>
  );
};

export default Header;
