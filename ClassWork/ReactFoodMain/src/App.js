import React, { useState } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router";
import Header from "./components/Header/Header";
import Body from "./components/Body/Body";
import Footer from "./components/Footer/Footer";
import ResMenu from "./components/Body/ResMenu"; 
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
    <Router>
      <Header
        city={city}
        onCityChange={handleCityChange}
        searchText={searchText}
        setSearchText={setSearchText}
        setTriggerSearch={setTriggerSearch}
      />

      {/* Define page routes */}
      <Routes>
        {/* Home — shows the restaurant grid */}
        <Route
          path="/"
          element={
            <Body
              city={city}
              lat={lat}
              lng={lng}
              searchText={searchText}
              triggerSearch={triggerSearch}
            />
          }
        />

       
        <Route path="/resmenu/:id" element={<ResMenu />} />
      </Routes>

      <Footer />
    </Router>
  );
};

export default App;
