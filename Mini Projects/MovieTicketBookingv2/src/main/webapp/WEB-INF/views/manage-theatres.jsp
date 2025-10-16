<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Theatres</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .theatre-card {
            transition: transform 0.3s;
            border-left: 4px solid #0dcaf0;
        }
        .theatre-card:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }
        .admin-badge {
            font-size: 0.8em;
        }
        .theatre-header {
            background-color: #f8f9fa;
            padding: 15px;
            border-radius: 5px 5px 0 0;
            border-bottom: 1px solid #dee2e6;
        }
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/views/header.jsp" />

    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2><i class="fas fa-film me-2"></i>Manage Theatres</h2>
            <a href="<c:url value='/admin/dashboard' />" class="btn btn-secondary">
                <i class="fas fa-arrow-left me-1"></i> Back to Dashboard
            </a>
        </div>

        <div class="row mb-4">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title"><i class="fas fa-user-shield me-2"></i>Theatre Admins</h5>
                        <div class="list-group">
                            <c:forEach var="admin" items="${theatreAdmins}">
                                <div class="list-group-item">
                                    <div class="d-flex justify-content-between align-items-center">
                                        <div>
                                            <h6 class="mb-0">${admin.userName}</h6>
                                            <small class="text-muted">${admin.email}</small>
                                        </div>
                                        <span class="badge bg-primary admin-badge">Admin ID: ${admin.userId}</span>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-6">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title"><i class="fas fa-plus me-2"></i>Assign Theatre to Admin</h5>
                        <form action="<c:url value='/admin/assign-theatre' />" method="post">
                            <div class="mb-3">
                                <label for="adminId" class="form-label">Theatre Admin</label>
                                <select id="adminId" name="adminId" class="form-select" required>
                                    <option value="">Select Admin</option>
                                    <c:forEach var="admin" items="${theatreAdmins}">
                                        <option value="${admin.userId}">${admin.userName} (${admin.email})</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="theatreId" class="form-label">Theatre</label>
                                <select id="theatreId" name="theatreId" class="form-select" required>
                                    <option value="">Select Theatre</option>
                                    <c:forEach var="theatre" items="${theatres}">
                                        <option value="${theatre.theatreId}">${theatre.theatreName} (${theatre.city})</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <button type="submit" class="btn btn-primary">
                                <i class="fas fa-link me-1"></i>Assign Theatre
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <h4 class="mb-3"><i class="fas fa-theater-masks me-2"></i>All Theatres</h4>

        <div class="row">
            <c:forEach var="theatre" items="${theatres}">
                <div class="col-md-4 mb-4">
                    <div class="card theatre-card">
                        <div class="theatre-header">
                            <h5 class="card-title mb-0">${theatre.theatreName}</h5>
                        </div>
                        <div class="card-body">
                            <p class="card-text">
                                <i class="fas fa-map-marker-alt me-1"></i>${theatre.address}<br>
                                <i class="fas fa-city me-1"></i>${theatre.city}
                            </p>

                            <div class="d-flex justify-content-between align-items-center mt-3">
                                <div>
                                    <span class="badge bg-primary">Theatre ID: ${theatre.theatreId}</span>
                                </div>
                                <div class="dropdown">
                                    <button class="btn btn-sm btn-outline-secondary dropdown-toggle" type="button"
                                            data-bs-toggle="dropdown" aria-expanded="false">
                                        <i class="fas fa-ellipsis-v"></i>
                                    </button>
                                    <ul class="dropdown-menu">
                                        <li><a class="dropdown-item" href="#"><i class="fas fa-edit me-1"></i>Edit</a></li>
                                        <li><a class="dropdown-item" href="#"><i class="fas fa-eye me-1"></i>View Shows</a></li>
                                        <li><a class="dropdown-item" href="#"><i class="fas fa-trash me-1"></i>Delete</a></li>
                                    </ul>
                                </div>
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
