<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Movie Ratings</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        .rating-display {
            font-weight: bold;
            color: #2a6496;
        }
        .error-message {
            color: red;
            font-weight: bold;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
    <h1>Movie Ratings</h1>

    <c:if test="${not empty error}">
        <p class="error-message">${error}</p>
    </c:if>

    <h2>Movies with Ratings</h2>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Genre</th>
                <th>Duration (mins)</th>
                <th>Release Date</th>
                <th>Average Rating</th>
                <th>Number of Ratings</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="movieRating" items="${movieRatings}">
                <tr>
                    <td>${movieRating.movieId}</td>
                    <td>${movieRating.title}</td>
                    <td>${movieRating.genre}</td>
                    <td>${movieRating.duration}</td>
                    <td>${movieRating.releaseDate}</td>
                    <td>
                        <c:choose>
                            <c:when test="${movieRating.averageRating > 0}">
                                <span class="rating-display">${movieRating.averageRating}</span>/5
                            </c:when>
                            <c:otherwise>
                                Not rated yet
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>${movieRating.ratingCount}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <p><a href="/bookmyshow/welcome">Back to Welcome Page</a></p>
</body>
</html>
