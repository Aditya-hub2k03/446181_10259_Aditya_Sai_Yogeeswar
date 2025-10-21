import React from "react";

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

const MovieGrid = () => {
  return (
    <section className="movies">
      <div className="section-head">
        <h2>Now Showing</h2>
        <div className="filters">
          <div className="chip">Top Rated</div>
          <div className="chip">New Releases</div>
          <div className="chip">Action</div>
          <div className="chip">Comedy</div>
        </div>
      </div>

      <div className="movies-grid">
        {movies.map((movie, index) => (
          <article key={index} className="card">
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
        ))}
      </div>
    </section>
  );
};

export default MovieGrid;
