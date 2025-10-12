<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="<c:url value='/' />">Movie Ticket Booking</a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value='/' />">Home</a>

                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value='/movie/search?keyword=' />">Search</a>

                </li>
            </ul>
            <ul class="navbar-nav">
                <c:choose>
                    <c:when test="${not empty sessionScope.userId}">
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value='/user/profile' />">Profile</a>

                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value='/user/logout' />">Logout</a>

                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value='/login' />">Login</a>

                        </li>
                        <li class="nav-item">
                <a class="nav-link" href="<c:url value='/register' />">Register</a>
            </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>
