<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Rate Movie</title>
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
        .rating-form {
            margin-bottom: 20px;
            padding: 15px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        .success-message {
            color: green;
            font-weight: bold;
            margin-bottom: 15px;
        }
        .error-message {
            color: red;
            font-weight: bold;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
    <h1>Rate Movie</h1>

    <c:if test="${not empty error}">
        <p class="error-message">${error}</p>
    </c:if>

    <c:if test="${not empty message}">
        <p class="success-message">${message}</p>
    </c:if>

    <c:if test="${not empty rating}">
        <div class="success-message">
            Your rating of ${rating.rating} for movie ID ${rating.movieId} has been saved!
        </div>
    </c:if>

    <h2>Available Movies</h2>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Genre</th>
                <th>Duration (mins)</th>
                <th>Release Date</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="movie" items="${movies}">
                <tr>
                    <td>${movie.movieId}</td>
                    <td>${movie.title}</td>
                    <td>${movie.genre}</td>
                    <td>${movie.duration}</td>
                    <td>${movie.releaseDate}</td>
                    <td>
                        <form action="rate-movie" method="post" class="rating-form">
                            <input type="hidden" name="movieId" value="${movie.movieId}">
                            <label for="rating_${movie.movieId}">Rating (1-5):</label>
                            <select id="rating_${movie.movieId}" name="rating" required>
                                <option value="">Select Rating</option>
                                <option value="1">1 - Poor</option>
                                <option value="2">2 - Fair</option>
                                <option value="3">3 - Good</option>
                                <option value="4">4 - Very Good</option>
                                <option value="5">5 - Excellent</option>
                            </select>
                            <button type="submit">Rate This Movie</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <p><a href="/bookmyshow/welcome">Back to Welcome Page</a></p>
</body>
</html>
