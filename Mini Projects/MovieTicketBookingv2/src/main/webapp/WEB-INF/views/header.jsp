<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="<c:url value='/' />">Movie Ticket Booking</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>

        <form class="d-flex ms-lg-3 me-lg-auto my-2 my-lg-0" action="<c:url value='/movie/search' />" method="get" role="search">
            <div class="input-group">
                <input class="form-control"
                       type="search"
                       name="keyword"
                       placeholder="Search movies, theatres..."
                       aria-label="Search"
                       value="${param.keyword}">
                <button class="btn btn-outline-light" type="submit">
                    <i class="fas fa-search"></i>
                </button>
            </div>
        </form>

        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value='/' />">Home</a>
                </li>
                <c:if test="${not empty sessionScope.userId}">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/movie/search' />">Movies</a>
                    </li>
                </c:if>
            </ul>

            <ul class="navbar-nav">
                <c:choose>
                    <c:when test="${not empty sessionScope.userId}">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                <i class="fas fa-user me-1"></i>${sessionScope.userName}
                            </a>
                            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="userDropdown">
                                <li><a class="dropdown-item" href="<c:url value='/user/profile' />"><i class="fas fa-user-circle me-2"></i>Profile</a></li>
                                <li><a class="dropdown-item" href="<c:url value='/user/change-password' />"><i class="fas fa-key me-2"></i>Change Password</a></li>

                                <!-- Check for roles -->
                                <c:set var="userRoles" value="${roleService.getRolesByUserId(sessionScope.userId)}"/>
                                <c:forEach var="role" items="${userRoles}">
                                    <c:if test="${role.roleName == 'APPLICATION_ADMIN'}">
                                        <li><hr class="dropdown-divider"></li>
                                        <li><a class="dropdown-item" href="<c:url value='/admin/dashboard' />"><i class="fas fa-user-shield me-2"></i>Admin Dashboard</a></li>
                                    </c:if>
                                    <c:if test="${role.roleName == 'THEATRE_ADMIN'}">
                                        <li><a class="dropdown-item" href="<c:url value='/theatre-admin/dashboard' />"><i class="fas fa-theater-masks me-2"></i>Theatre Admin</a></li>
                                    </c:if>
                                </c:forEach>

                                <li><hr class="dropdown-divider"></li>
                                <li><a class="dropdown-item" href="<c:url value='/user/logout' />"><i class="fas fa-sign-out-alt me-2"></i>Logout</a></li>
                            </ul>
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

<!-- Add Font Awesome for icons -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">