<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>${movie.title}</title>
</head>
<body>
<h1>${movie.title}</h1>
<img src="${movie.posterUrl}" alt="${movie.title}" width="300" height="450"/>
<p><b>Genre:</b> ${movie.genre}</p>
<p><b>Language:</b> ${movie.language}</p>
<p><b>Release Date:</b> ${movie.releaseDate}</p>
<p><b>Synopsis:</b> ${movie.synopsis}</p>

<a href="/movie/${movie.movieId}/theatres">Book Tickets</a>
</body>
</html>
