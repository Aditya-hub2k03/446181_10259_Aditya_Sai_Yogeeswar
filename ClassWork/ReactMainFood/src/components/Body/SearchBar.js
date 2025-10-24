import React from 'react';

const SearchBar = () => {
  return (
    <section className="search-bar">
      <input type="text" placeholder="Search for food or restaurants..." className="search-input"/>
      <button className="search-btn">Search</button>
    </section>
  );
};

export default SearchBar;
