import React, { useState } from "react";
import Header from "./components/Header/Header";
import Body from "./components/Body/Body";
import Footer from "./components/Footer/Footer";
import "./styles.css";

const App = () => {
  const [city, setCity] = useState("Hyderabad");
  const [lat, setLat] = useState(17.3843);
  const [lng, setLng] = useState(78.4583);
  const [searchText, setSearchText] = useState("");
  const [triggerSearch, setTriggerSearch] = useState(false);

  const handleCityChange = (lat, lng, cityName) => {
    setLat(lat);
    setLng(lng);
    setCity(cityName);
  };

  return (
    <>
      <Header
        city={city}
        onCityChange={handleCityChange}
        searchText={searchText}
        setSearchText={setSearchText}
        setTriggerSearch={setTriggerSearch}
      />
      <Body
        city={city}
        lat={lat}
        lng={lng}
        searchText={searchText}
        triggerSearch={triggerSearch}
      />
      <Footer />
    </>
  );
};

export default App;
