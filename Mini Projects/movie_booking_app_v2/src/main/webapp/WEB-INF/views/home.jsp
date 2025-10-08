<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Movie Booking - Home</title>
    <style>
        .movie-card { display: inline-block; width: 180px; margin: 10px; text-align: center; vertical-align: top; }
        .movie-card img { width: 100%; height: 250px; border-radius: 8px; }
    </style>
</head>
<body>
<h2>Welcome to Movie Booking</h2>

<h3>Top Rated Movies</h3>
<div>
    <c:forEach var="movie" items="${topRatedMovies}">
        <div class="movie-card">
            <a href="${pageContext.request.contextPath}/movie/${movie.movieId}">
                <img src="${movie.posterUrl}" alt="${movie.title}" />
                <p>${movie.title}</p>
            </a>
        </div>
    </c:forEach>
</div>

<h3>New Releases</h3>
<div>
    <c:forEach var="movie" items="${newReleases}">
        <div class="movie-card">
            <a href="${pageContext.request.contextPath}/movie/${movie.movieId}">
                <img src="${movie.posterUrl}" alt="${movie.title}" />
                <p>${movie.title}</p>
            </a>
        </div>
    </c:forEach>
</div>

<h3>Genres</h3>
<div>
    <c:forEach var="movie" items="${genreBasedMovies}">
        <div class="movie-card">
            <a href="${pageContext.request.contextPath}/movie/${movie.movieId}">
                <img src="${movie.posterUrl}" alt="${movie.title}" />
                <p>${movie.title}</p>
            </a>
        </div>
    </c:forEach>
</div>

<h3>Languages</h3>
<div>
    <c:forEach var="movie" items="${languageBasedMovies}">
        <div class="movie-card">
            <a href="${pageContext.request.contextPath}/movie/${movie.movieId}">
                <img src="${movie.posterUrl}" alt="${movie.title}" />
                <p>${movie.title}</p>
            </a>
        </div>
    </c:forEach>
</div>

<h3>Cities</h3>
<ul>
    <c:forEach var="city" items="${cities}">
        <li>${city}</li>
    </c:forEach>
</ul>

<a href="${pageContext.request.contextPath}/login">Login</a> | 
<a href="${pageContext.request.contextPath}/signup">Sign Up</a>
</body>
</html>
