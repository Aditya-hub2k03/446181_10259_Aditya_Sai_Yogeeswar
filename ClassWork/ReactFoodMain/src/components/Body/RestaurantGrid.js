import React, { useEffect, useState } from "react";
import Shimmer from "./Shimmer";
import { useNavigate } from "react-router";

const RestaurantGrid = ({ city, lat, lng, searchText, triggerSearch }) => {
  const [restaurants, setRestaurants] = useState([]);
  const [filtered, setFiltered] = useState([]);
  const [loading, setLoading] = useState(false);
  const [activeFilter, setActiveFilter] = useState("All");
  const navigate = useNavigate();

  const FILTERS = [
    { name: "All" },
    { name: "Highest Rated" },
    { name: "Rating 4+" },
    { name: "Cost Low to High" },
  ];

  const fetchData = async () => {
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

      setRestaurants(list);
    } catch (err) {
      console.error("Error fetching restaurants:", err);
    }
    setLoading(false);
  };

  useEffect(() => {
    setRestaurants([]);
    setFiltered([]);
    fetchData();
  }, [lat, lng]);

  useEffect(() => {
    let temp = [...restaurants];
    if (searchText) {
      temp = temp.filter((r) =>
        r.info.name.toLowerCase().includes(searchText.toLowerCase())
      );
    }

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
      id,
    } = info;

    const costDisplay = costForTwo
      ? `₹${costForTwo / 100} for two`
      : "Cost not available";
    const ratingDisplay = avgRating ? ` ${avgRating}` : "Rating N/A";

    return (
      <div
        className="restaurant-card"
        key={`${id}-${index}`}
        onClick={() => navigate(`/resmenu/${id}`)}
        style={{ cursor: "pointer" }}        
      >
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

      {loading ? (
        <Shimmer />
      ) : (
        <div className="restaurant-grid">
          {filtered.map((res, index) => (
            <ResCard key={`${res.info.id}-${index}`} resObj={res} index={index} />
          ))}
        </div>
      )}
    </div>
  );
};

export default RestaurantGrid;
