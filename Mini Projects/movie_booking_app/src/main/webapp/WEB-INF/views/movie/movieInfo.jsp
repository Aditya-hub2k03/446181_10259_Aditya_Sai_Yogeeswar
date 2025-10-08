<%@ include file="../common/header.jsp" %>
<h3>${movie.title}</h3>

<img src="${movie.posterUrl}" width="150">
<p>${movie.synopsis}</p>
<p>Language: ${movie.language}</p>
<p>Genre: ${movie.genre}</p>
<p>Release Date: ${movie.releaseDate}</p>
<p>⭐ ${movie.rating}</p>

<a href="shows?movieId=${movie.movieId}">🎟 Book Tickets</a>
<%@ include file="../common/footer.jsp" %>
