import React from "react";

const SearchBar = ({ searchText, setSearchText, handleSearch }) => (
  <div className="search-bar">
    <input
      type="search"
      placeholder="Search food or restaurants..."
      value={searchText}
      onChange={(e) => setSearchText(e.target.value)}
    />
    <button className="search-btn" onClick={handleSearch}>
      Search
    </button>
  </div>
);

export default SearchBar;
