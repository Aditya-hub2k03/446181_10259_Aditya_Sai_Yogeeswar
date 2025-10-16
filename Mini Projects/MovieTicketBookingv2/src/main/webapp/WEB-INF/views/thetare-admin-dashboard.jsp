<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Theatre Admin Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .theatre-card {
            transition: transform 0.3s;
            border-left: 4px solid #198754;
        }
        .theatre-card:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }
        .show-badge {
            font-size: 0.8em;
        }
        .dashboard-header {
            background: linear-gradient(135deg, #20c997, #198754);
            color: white;
            padding: 20px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/views/header.jsp" />

    <div class="container mt-4">
        <div class="dashboard-header">
            <h2><i class="fas fa-theater-masks me-2"></i>Theatre Admin Dashboard</h2>
            <p class="mb-0">Manage the theatres you're responsible for and their shows.</p>
        </div>

        <div class="row mb-4">
            <div class="col-md-12">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title"><i class="fas fa-film me-2"></i>Your Theatres</h5>
                        <p class="card-text">Theatres you are responsible for managing:</p>

                        <div class="row">
                            <c:forEach var="theatre" items="${theatres}">
                                <div class="col-md-4 mb-3">
                                    <div class="card theatre-card h-100">
                                        <div class="card-body">
                                            <h6 class="card-title">${theatre.theatreName}</h6>
                                            <p class="card-text">
                                                <i class="fas fa-map-marker-alt me-1"></i>${theatre.city}<br>
                                                <small class="text-muted">${theatre.address}</small>
                                            </p>
                                            <a href="<c:url value='/theatre-admin/manage-shows' />?theatreId=${theatre.theatreId}"
                                               class="btn btn-sm btn-primary">
                                                <i class="fas fa-calendar-alt me-1"></i>Manage Shows
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6 mb-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title"><i class="fas fa-chart-line me-2"></i>Your Statistics</h5>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="card bg-light mb-3">
                                    <div class="card-body text-center">
                                        <h6 class="card-title">Theatres Managed</h6>
                                        <p class="card-text display-6">${fn:length(theatres)}</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="card bg-light mb-3">
                                    <div class="card-body text-center">
                                        <h6 class="card-title">Total Shows</h6>
                                        <p class="card-text display-6">42</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="card bg-light mb-3">
                                    <div class="card-body text-center">
                                        <h6 class="card-title">Bookings</h6>
                                        <p class="card-text display-6">1,250</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="card bg-light mb-3">
                                    <div class="card-body text-center">
                                        <h6 class="card-title">Revenue</h6>
                                        <p class="card-text display-6">₹5,250,000</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-6 mb-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title"><i class="fas fa-bell me-2"></i>Recent Activity</h5>
                        <div class="list-group">
                            <a href="#" class="list-group-item list-group-item-action">
                                <div class="d-flex w-100 justify-content-between">
                                    <h6 class="mb-1">New show added</h6>
                                    <small>2 hours ago</small>
                                </div>
                                <p class="mb-1">Show for "Inception" added at PVR Cinemas</p>
                            </a>
                            <a href="#" class="list-group-item list-group-item-action">
                                <div class="d-flex w-100 justify-content-between">
                                    <h6 class="mb-1">Show updated</h6>
                                    <small>5 hours ago</small>
                                </div>
                                <p class="mb-1">Show time updated for "The Dark Knight"</p>
                            </a>
                            <a href="#" class="list-group-item list-group-item-action">
                                <div class="d-flex w-100 justify-content-between">
                                    <h6 class="mb-1">Booking confirmed</h6>
                                    <small>1 day ago</small>
                                </div>
                                <p class="mb-1">Booking #1005 confirmed for 4 seats</p>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="/WEB-INF/views/footer.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
