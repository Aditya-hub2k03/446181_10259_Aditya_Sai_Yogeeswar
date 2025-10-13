import React from "react";
import { createRoot } from "react-dom/client";

// Sample movie data
const movies = [
  {
    title: "Inglourious Basterds",
    desc: "War Action · 2h 33m · English",
    rating: "★ 8.4",
    img: "https://m.media-amazon.com/images/M/MV5BODZhMWJlNjYtNDExNC00MTIzLTllM2ItOGQ2NGVjNDQ3MzkzXkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg"
  },
  {
    title: "Fight Club",
    desc: "Action · 2h 19m · English",
    rating: "★ 8.8",
    img: "https://m.media-amazon.com/images/M/MV5BOTgyOGQ1NDItNGU3Ny00MjU3LTg2YWEtNmEyYjBiMjI1Y2M5XkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg"
  },
  {
    title: "The Texas Chain Saw Massacre",
    desc: "Horror · 1h 23m · English",
    rating: "★ 7.4",
    img: "https://m.media-amazon.com/images/M/MV5BYjE1MGJkMjUtY2VkNi00N2U1LWI2NWEtMDExNGYzYjRkZTM0XkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg"
  },
  {
    title: "Django Unchained",
    desc: "Western · 2h 45m · English",
    rating: "★ 8.5",
    img: "https://m.media-amazon.com/images/M/MV5BMjIyNTQ5NjQ1OV5BMl5BanBnXkFtZTcwODg1MDU4OA@@._V1_FMjpg_UX1000_.jpg"
  }
];

// Components
const Header = () => (
  <header>
    <div className="brand">
      <div className="logo">MB</div>
      <div>
        <h1>MovieBox</h1>
        <p>Book tickets · Discover · Watch</p>
      </div>
    </div>
    <nav className="controls">
      <button className="btn">Sign in</button>
      <button className="btn primary">Register</button>
    </nav>
  </header>
);

const Hero = () => (
  <div className="hero">
    <div className="search">
      <input type="search" placeholder="Search movies, actors, genres..." />
      <div className="select">
        <select>
          {["All cities","Visakhapatnam","Chennai","New Delhi","Mumbai","Bengaluru","Kolkata"].map((c, i) => (
            <option key={i}>{c}</option>
          ))}
        </select>
        <select>
          {["All languages","English","Hindi","Telugu","Tamil","Malayalam","Kannada"].map((c, i) => (
            <option key={i}>{c}</option>
          ))}
        </select>
      </div>
    </div>
    <div className="search-btn">
      <button>Search</button>
    </div>
  </div>
);

const MovieCard = ({ movie }) => (
  <article className="card">
    <a href="#" className="poster">
      <img src={movie.img} alt={movie.title} />
      <div className="rating">{movie.rating}</div>
    </a>
    <div className="meta">
      <strong>{movie.title}</strong>
      <p>{movie.desc}</p>
    </div>
    <div className="actions">
      <button>Details</button>
      <button className="book">Book</button>
    </div>
  </article>
);

const MoviesSection = () => (
  <section className="movies">
    <div className="section-head">
      <h2>Now Showing</h2>
      <div className="filters">
        {["Top Rated","New Releases","Action","Comedy"].map((f, i) => (
          <div key={i} className="chip">{f}</div>
        ))}
      </div>
    </div>
    <div className="movies-grid">
      {movies.map((m, i) => <MovieCard key={i} movie={m} />)}
    </div>
  </section>
);

const Sidebar = () => (
  <aside className="sidebar">
    <div className="location">
      <label htmlFor="city">Your location</label>
      <select id="city">
        {["Visakhapatnam","New Delhi","Mumbai","Bengaluru","Kolkata","Chennai"].map((c, i) => (
          <option key={i}>{c}</option>
        ))}
      </select>
      <label htmlFor="theatre">Preferred theatre (optional)</label>
      <input id="theatre" placeholder="Search theatre name" />
    </div>
  </aside>
);

const App = () => (
  <div className="container">
    <Header />
    <Hero />
    <main className="content">
      <MoviesSection />
      <Sidebar />
    </main>
  </div>
);

// Render
const root = createRoot(document.getElementById("root"));
root.render(<App />);
