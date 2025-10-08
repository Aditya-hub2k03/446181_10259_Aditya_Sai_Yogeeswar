<%@ include file="../common/header.jsp" %>
<h3>🎥 Now Showing</h3>

<form action="search" method="get">
    <input type="text" name="keyword" placeholder="Search for a movie..." required>
    <button type="submit">Search</button>
</form>

<div>
    <h4>Top Rated Movies</h4>
    <c:forEach var="m" items="${topMovies}">
        <div>
            <img src="${m.posterUrl}" alt="${m.title}" width="100">
            <p><a href="movie/${m.movieId}">${m.title}</a></p>
            <p>⭐ ${m.rating}</p>
        </div>
    </c:forEach>
</div>

<%@ include file="../common/footer.jsp" %>
