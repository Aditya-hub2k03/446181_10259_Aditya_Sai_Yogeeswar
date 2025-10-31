import React from "react";
import { useParams, Link } from "react-router";

const MENU_URL = "https://www.swiggy.com/dapi/menu/pl?page-type=REGULAR_MENU&complete-menu=true&lat=0&lng=0&restaurantId=$(id)&catalog_qa=undefined&submitAction=ENTER";
const ResMenu = () => {
  const { id } = useParams();

  const dummyData = {
    name: "Spicy Treats",
    address: "123 Food Street, Hyderabad",
    rating: "4.5 ",
    menu: [
      { name: "Paneer Butter Masala", price: "₹220" },
      { name: "Chicken Biryani", price: "₹280" },
      { name: "Butter Naan", price: "₹40" },
      { name: "Gulab Jamun", price: "₹100" },
    ],
  };

  return (
    <div className="resmenu">
      <h2>{dummyData.name}</h2>
      <p>
        <strong>ID:</strong> {id}
      </p>
      <p>
        <strong>Address:</strong> {dummyData.address}
      </p>
      <p>
        <strong>Rating:</strong> {dummyData.rating}
      </p>

      <h3>Menu</h3>
      <ul>
        {dummyData.menu.map((item, index) => (
          <li key={index}>
            {item.name} - {item.price}
          </li>
        ))}
      </ul>

      <Link to="/" className="back-link">
        ← Back to Restaurants
      </Link>
    </div>
  );
};

export default ResMenu;
