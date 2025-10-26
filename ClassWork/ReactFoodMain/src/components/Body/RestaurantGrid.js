import React, { useEffect, useState } from "react";
import Shimmer from "./Shimmer";

const RestaurantGrid = ({ city, lat, lng, searchText, triggerSearch }) => {
  const [restaurants, setRestaurants] = useState([]);
  const [filtered, setFiltered] = useState([]);
  const [loading, setLoading] = useState(false);
  const [hasMore, setHasMore] = useState(true);
  const [activeFilter, setActiveFilter] = useState("All");

  const FILTERS = [
    { name: "All" },
    { name: "Highest Rated" },
    { name: "Rating 4+" },
    { name: "Cost Low to High" },
  ];

  const fetchData = async () => {
    if (!hasMore || loading) return;
    setLoading(true);

    try {
      const API_URL = `https://www.swiggy.com/dapi/restaurants/list/v5?lat=${lat}&lng=${lng}&is-seo-homepage-enabled=true&page_type=DESKTOP_WEB_LISTING`;
      const res = await fetch(API_URL);
      const data = await res.json();

      let list = [];
      if (data?.data?.cards?.length > 0) {
        data.data.cards.forEach((card) => {
          const restaurants =
            card?.card?.card?.gridElements?.infoWithStyle?.restaurants;
          if (restaurants && restaurants.length > 0) {
            list = list.concat(restaurants);
          }
        });
      }

      if (list.length === 0) {
        setHasMore(false);
        setLoading(false);
        return;
      }

      // Append new restaurants instead of overwriting
      setRestaurants((prev) => [...prev, ...list]);
    } catch (err) {
      console.error("Error fetching restaurants:", err);
    }

    setLoading(false);
  };

  // Infinite scroll
  const handleScroll = () => {
    if (
      window.innerHeight + document.documentElement.scrollTop + 200 >=
      document.documentElement.scrollHeight
    ) {
      fetchData();
    }
  };

  useEffect(() => {
    window.addEventListener("scroll", handleScroll, { passive: true });
    return () => window.removeEventListener("scroll", handleScroll);
  }, []);

  // Refetch on city change
  useEffect(() => {
    setRestaurants([]);
    setFiltered([]);
    setHasMore(true);
    fetchData();
  }, [lat, lng]);

  // Apply search + filters whenever restaurants, searchText, or activeFilter change
  useEffect(() => {
    let temp = [...restaurants];

    // Apply search
    if (searchText) {
      temp = temp.filter((r) =>
        r.info.name.toLowerCase().includes(searchText.toLowerCase())
      );
    }

    // Apply filter
    switch (activeFilter) {
      case "Highest Rated":
        temp = temp.filter((r) => r.info.avgRating >= 4.5);
        break;
      case "Rating 4+":
        temp = temp.filter((r) => r.info.avgRating >= 4);
        break;
      case "Cost Low to High":
        temp.sort(
          (a, b) => (a.info.costForTwo || 0) - (b.info.costForTwo || 0)
        );
        break;
      default:
        break;
    }

    setFiltered(temp);
  }, [restaurants, searchText, triggerSearch, activeFilter]);

  // Embedded ResCard
  const ResCard = ({ resObj, index }) => {
    const { info } = resObj;
    const {
      cloudinaryImageId,
      name,
      locality,
      costForTwo,
      areaName,
      cuisines,
      avgRating,
    } = info;

    const costDisplay = costForTwo
      ? `₹${costForTwo / 100} for two`
      : "Cost not available";
    const ratingDisplay = avgRating ? `★ ${avgRating}` : "Rating N/A";

    return (
      <div className="restaurant-card" key={`${info.id}-${index}`}>
        <img
          className="restaurant-img"
          src={`https://media-assets.swiggy.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_300/${cloudinaryImageId}`}
          alt={name}
        />
        <div className="meta">
          <strong>{name}</strong>
          <p>{locality || "Location N/A"}</p>
          <p>{costDisplay}</p>
          <p>{areaName || "Area N/A"}</p>
          <p>{cuisines?.join(", ") || "Cuisines N/A"}</p>
        </div>
        <div className="rating">{ratingDisplay}</div>
      </div>
    );
  };

  return (
    <div>
      <h2 className="section-title">Restaurants in {city}</h2>

      {/* Filter Chips */}
      <div className="filter-chips">
        {FILTERS.map((filter) => (
          <div
            key={filter.name}
            className={`chip ${activeFilter === filter.name ? "active" : ""}`}
            onClick={() => setActiveFilter(filter.name)}
          >
            {filter.name}
          </div>
        ))}
      </div>

      {/* Restaurant Grid */}
      <div className="restaurant-grid">
        {filtered.map((res, index) => (
          <ResCard key={`${res.info.id}-${index}`} resObj={res} index={index} />
        ))}
        {loading && <Shimmer />}
      </div>

      {!hasMore && <p className="end-msg">You reached the end!</p>}
    </div>
  );
};

export default RestaurantGrid;
