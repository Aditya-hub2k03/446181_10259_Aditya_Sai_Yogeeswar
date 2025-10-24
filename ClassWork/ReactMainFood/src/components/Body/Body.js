import React from 'react';
import SearchBar from './SearchBar';
import Carousel from './Carousel';
import Filters from './Filters';
import RestaurantGrid from './RestaurantGrid';

const Body = () => {
  return (
    <main className="main">
      <SearchBar />
      <Carousel />
      <Filters />
      <RestaurantGrid />
    </main>
  );
};

export default Body;
