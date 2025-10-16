<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Users</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .user-card {
            transition: transform 0.3s;
            border-left: 4px solid #0d6efd;
        }
        .user-card:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }
        .role-badge {
            font-size: 0.8em;
        }
        .search-container {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/views/header.jsp" />

    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2><i class="fas fa-users me-2"></i>Manage Users</h2>
            <a href="<c:url value='/admin/dashboard' />" class="btn btn-secondary">
                <i class="fas fa-arrow-left me-1"></i> Back to Dashboard
            </a>
        </div>

        <div class="search-container">
            <div class="input-group mb-3">
                <input type="text" class="form-control" placeholder="Search users by name or email...">
                <button class="btn btn-outline-secondary" type="button">
                    <i class="fas fa-search"></i>
                </button>
            </div>
        </div>

        <div class="row">
            <c:forEach var="user" items="${users}">
                <div class="col-md-4 mb-4">
                    <div class="card user-card">
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-start">
                                <div>
                                    <h5 class="card-title mb-1">${user.userName}</h5>
                                    <p class="card-text text-muted mb-2">${user.email}</p>
                                </div>
                                <div class="dropdown">
                                    <button class="btn btn-sm btn-outline-secondary dropdown-toggle" type="button"
                                            data-bs-toggle="dropdown" aria-expanded="false">
                                        <i class="fas fa-ellipsis-v"></i>
                                    </button>
                                    <ul class="dropdown-menu">
                                        <li><a class="dropdown-item" href="#"><i class="fas fa-edit me-1"></i>Edit</a></li>
                                        <li><a class="dropdown-item" href="#"><i class="fas fa-ban me-1"></i>Suspend</a></li>
                                        <li><a class="dropdown-item" href="#"><i class="fas fa-trash me-1"></i>Delete</a></li>
                                    </ul>
                                </div>
                            </div>

                            <div class="mb-2">
                                <small class="text-muted"><i class="fas fa-map-marker-alt me-1"></i>${user.city}</small>
                            </div>

                            <div class="mb-3">
                                <span class="badge bg-primary role-badge">User ID: ${user.userId}</span>
                                <c:if test="${user.preferredGenre != null && !user.preferredGenre.empty}">
                                    <span class="badge bg-info role-badge ms-1">${user.preferredGenre}</span>
                                </c:if>
                            </div>

                            <div class="d-flex flex-wrap gap-2">
                                <c:forEach var="role" items="${user.roles}">
                                    <span class="badge bg-success role-badge">
                                        <i class="fas fa-user-tag me-1"></i>${role.roleName}
                                    </span>
                                </c:forEach>
                            </div>

                            <hr>

                            <div class="d-flex justify-content-between">
                                <small class="text-muted">Joined: <c:if test="${not empty user.createdOn}"><fmt:formatDate value="${user.createdOn}" pattern="MMM dd, yyyy"/></c:if></small>
                                <form action="<c:url value='/admin/assign-role' />" method="post" class="d-flex">
                                    <input type="hidden" name="userId" value="${user.userId}">
                                    <select name="roleId" class="form-select form-select-sm me-2" style="width: 150px;">
                                        <option value="">Assign Role</option>
                                        <c:forEach var="role" items="${roles}">
                                            <option value="${role.roleId}">${role.roleName}</option>
                                        </c:forEach>
                                    </select>
                                    <button type="submit" class="btn btn-sm btn-primary">
                                        <i class="fas fa-user-plus me-1"></i>Assign
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <jsp:include page="/WEB-INF/views/footer.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
