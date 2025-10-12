<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Movie Ticket Booking - Reviews</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="/WEB-INF/views/header.jsp" />

    <div class="container mt-4">
        <h2>Reviews</h2>

        <c:if test="${param.reviewSuccess != null}">
            <div class="alert alert-success">Review added successfully!</div>
        </c:if>

        <div class="mb-3">
            <a href="<c:url value='/movie/details?movieId=${movieId}' />" class="btn btn-secondary">Back to Movie</a>
        </div>

        <div class="mb-4">
            <h3>Add Review</h3>
            <form action="<c:url value='/review/add' />" method="post">
                <input type="hidden" name="movieId" value="${movieId}" />
                <div class="mb-3">
                    <label for="rating" class="form-label">Rating (1-10)</label>
                    <input type="number" class="form-control" id="rating" name="rating" min="1" max="10" required>
                </div>
                <div class="mb-3">
                    <label for="comment" class="form-label">Comment</label>
                    <textarea class="form-control" id="comment" name="comment" rows="3" required></textarea>
                </div>
                <button type="submit" class="btn btn-primary">Submit Review</button>
            </form>
        </div>

        <h3>User Reviews</h3>
        <c:if test="${not empty reviews}">
            <div class="list-group">
                <c:forEach var="review" items="${reviews}">
                    <div class="list-group-item">
                        <div class="d-flex w-100 justify-content-between">
                            <h5 class="mb-1">Rating: ${review.rating}/10</h5>
                            <small><fmt:formatDate value="${review.createdOn}" pattern="dd-MM-yyyy HH:mm" /></small>
                        </div>
                        <p class="mb-1">${review.comment}</p>
                    </div>
                </c:forEach>
            </div>
        </c:if>
        <c:if test="${empty reviews}">
            <div class="alert alert-info">No reviews yet.</div>
        </c:if>
    </div>

    <jsp:include page="/WEB-INF/views/footer.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
