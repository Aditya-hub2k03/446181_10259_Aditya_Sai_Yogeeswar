<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Movie Ticket Booking - ${movie.title}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="/WEB-INF/views/header.jsp" />

    <div class="container mt-4">
        <div class="row">
            <div class="col-md-4">
                <img src="${movie.posterUrl}" class="img-fluid" alt="${movie.title}">
            </div>
            <div class="col-md-8">
                <h2>${movie.title}</h2>
                <p><strong>Genre:</strong> ${movie.genre}</p>
                <p><strong>Language:</strong> ${movie.language}</p>
                <p><strong>Release Date:</strong> <fmt:formatDate value="${movie.releaseDate}" pattern="dd-MM-yyyy" /></p>
                <p><strong>Rating:</strong> ${movie.rating}/10</p>
                <p><strong>Synopsis:</strong> ${movie.synopsis}</p>
                <p><strong>Cast:</strong> ${movie.cast}</p>
                <p><strong>Crew:</strong> ${movie.crew}</p>
                <p><strong>Formats:</strong> ${movie.formats}</p>
                <a href="<c:url value='/theatre/list?movieId=${movie.movieId}' />" class="btn btn-primary">Book Tickets</a>

            </div>
        </div>
    </div>

    <jsp:include page="/WEB-INF/views/footer.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
