import React, { useState } from 'react';

const Header = () => {
  const [darkMode, setDarkMode] = useState(false);

  const toggleDarkMode = () => setDarkMode(!darkMode);

  return (
    <header className={darkMode ? 'header dark-mode' : 'header'}>
      <div className="logo-section">
        <img src="https://cdn-icons-png.flaticon.com/512/857/857681.png" alt="Logo" className="logo" />
        <h1 className="app-name">FoodieGo</h1>
      </div>

      <div className="location-section">
        <select className="location-dropdown">
          <option>📍 Chennai</option>
          <option>Bangalore</option>
          <option>Hyderabad</option>
          <option>Mumbai</option>
          <option>Delhi</option>
        </select>
      </div>

      <div className="header-right">
        <button onClick={toggleDarkMode}>
          {darkMode ? '☀️' : '🌙'}
        </button>
        <button className="login-btn">Login</button>
        <button className="signup-btn">Sign Up</button>
      </div>
    </header>
  );
};

export default Header;
