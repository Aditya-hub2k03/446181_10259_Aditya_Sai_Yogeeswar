<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Shows</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
    <style>
        .show-card {
            transition: transform 0.3s;
            border-left: 4px solid #fd7e14;
        }
        .show-card:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }
        .format-badge {
            font-size: 0.8em;
        }
        .time-badge {
            font-size: 0.9em;
            font-weight: 500;
        }
        .add-show-btn {
            position: fixed;
            bottom: 20px;
            right: 20px;
            width: 50px;
            height: 50px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            box-shadow: 0 2px 10px rgba(0,0,0,0.2);
        }
        .modal-content {
            border-radius: 10px;
        }
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/views/header.jsp" />

    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2><i class="fas fa-calendar-alt me-2"></i>Manage Shows</h2>
            <a href="<c:url value='/theatre-admin/dashboard' />" class="btn btn-secondary">
                <i class="fas fa-arrow-left me-1"></i> Back to Dashboard
            </a>
        </div>

        <div class="card mb-4">
            <div class="card-body">
                <h5 class="card-title">
                    <i class="fas fa-film me-2"></i>
                    Shows for Theatre ID: ${theatreId}
                </h5>
                <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addShowModal">
                    <i class="fas fa-plus me-1"></i> Add New Show
                </button>
            </div>
        </div>

        <div class="row">
            <c:forEach var="show" items="${shows}">
                <div class="col-md-4 mb-4">
                    <div class="card show-card h-100">
                        <div class="card-body">
                            <div class="d-flex justify-content-between">
                                <div>
                                    <h5 class="card-title mb-1">${show.movieTitle}</h5>
                                    <span class="badge format-badge bg-primary">${show.format}</span>
                                </div>
                                <div class="dropdown">
                                    <button class="btn btn-sm btn-outline-secondary dropdown-toggle" type="button"
                                            data-bs-toggle="dropdown" aria-expanded="false">
                                        <i class="fas fa-ellipsis-v"></i>
                                    </button>
                                    <ul class="dropdown-menu">
                                        <li>
                                            <a class="dropdown-item" href="#" data-bs-toggle="modal"
                                               data-bs-target="#editShowModal${show.showId}">
                                                <i class="fas fa-edit me-1"></i>Edit
                                            </a>
                                        </li>
                                        <li>
                                            <a class="dropdown-item" href="#">
                                                <i class="fas fa-trash me-1"></i>Delete
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </div>

                            <div class="mt-3">
                                <div class="d-flex align-items-center mb-2">
                                    <i class="fas fa-calendar-day me-2"></i>
                                    <span><fmt:formatDate value="${show.showDate}" pattern="MMM dd, yyyy"/></span>
                                </div>
                                <div class="d-flex align-items-center">
                                    <i class="fas fa-clock me-2"></i>
                                    <span class="badge time-badge bg-success">
                                        <fmt:formatDate value="${show.showTime}" pattern="hh:mm a"/>
                                    </span>
                                </div>
                            </div>

                            <div class="mt-3 pt-2 border-top">
                                <small class="text-muted">Show ID: ${show.showId}</small>
                            </div>
                        </div>
                    </div>

                    <!-- Edit Show Modal -->
                    <div class="modal fade" id="editShowModal${show.showId}" tabindex="-1"
                         aria-labelledby="editShowModalLabel${show.showId}" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="editShowModalLabel${show.showId}">Edit Show</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <form action="<c:url value='/theatre-admin/update-show' />" method="post">
                                    <div class="modal-body">
                                        <input type="hidden" name="showId" value="${show.showId}">

                                        <div class="mb-3">
                                            <label for="editShowDate${show.showId}" class="form-label">Date</label>
                                            <input type="text" class="form-control flatpickr-date"
                                                   id="editShowDate${show.showId}" name="showDate"
                                                   value="<fmt:formatDate value='${show.showDate}' pattern='yyyy-MM-dd'/>"
                                                   required>
                                        </div>

                                        <div class="mb-3">
                                            <label for="editShowTime${show.showId}" class="form-label">Time</label>
                                            <input type="text" class="form-control flatpickr-time"
                                                   id="editShowTime${show.showId}" name="showTime"
                                                   value="<fmt:formatDate value='${show.showTime}' pattern='HH:mm'/>"
                                                   required>
                                        </div>

                                        <div class="mb-3">
                                            <label for="editShowFormat${show.showId}" class="form-label">Format</label>
                                            <select class="form-select" id="editShowFormat${show.showId}" name="format" required>
                                                <option value="2D" ${show.format == '2D' ? 'selected' : ''}>2D</option>
                                                <option value="3D" ${show.format == '3D' ? 'selected' : ''}>3D</option>
                                                <option value="IMAX" ${show.format == 'IMAX' ? 'selected' : ''}>IMAX</option>
                                                <option value="4DX" ${show.format == '4DX' ? 'selected' : ''}>4DX</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                        <button type="submit" class="btn btn-primary">Save changes</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

        <!-- Add Show Modal -->
        <div class="modal fade" id="addShowModal" tabindex="-1" aria-labelledby="addShowModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addShowModalLabel">Add New Show</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <form action="<c:url value='/theatre-admin/add-show' />" method="post">
                        <div class="modal-body">
                            <input type="hidden" name="theatreId" value="${theatreId}">

                            <div class="mb-3">
                                <label for="movieId" class="form-label">Movie</label>
                                <select id="movieId" name="movieId" class="form-select" required>
                                    <option value="">Select Movie</option>
                                    <c:forEach var="movie" items="${movies}">
                                        <option value="${movie.movieId}">${movie.title}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label for="showDate" class="form-label">Date</label>
                                <input type="text" class="form-control flatpickr-date" id="showDate" name="showDate" required>
                            </div>

                            <div class="mb-3">
                                <label for="showTime" class="form-label">Time</label>
                                <input type="text" class="form-control flatpickr-time" id="showTime" name="showTime" required>
                            </div>

                            <div class="mb-3">
                                <label for="format" class="form-label">Format</label>
                                <select id="format" name="format" class="form-select" required>
                                    <option value="2D">2D</option>
                                    <option value="3D">3D</option>
                                    <option value="IMAX">IMAX</option>
                                    <option value="4DX">4DX</option>
                                </select>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-primary">Add Show</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <button class="btn btn-primary add-show-btn" data-bs-toggle="modal" data-bs-target="#addShowModal">
            <i class="fas fa-plus"></i>
        </button>
    </div>

    <jsp:include page="/WEB-INF/views/footer.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
    <script>
        // Initialize date pickers
        document.addEventListener('DOMContentLoaded', function() {
            // Date picker for add show modal
            flatpickr(".flatpickr-date", {
                dateFormat: "Y-m-d",
                minDate: "today"
            });

            // Time picker for add show modal
            flatpickr(".flatpickr-time", {
                enableTime: true,
                noCalendar: true,
                dateFormat: "H:i",
                time_24hr: true
            });

            // Initialize date pickers for edit modals
            document.querySelectorAll('.flatpickr-date, .flatpickr-time').forEach(function(element) {
                if (element.classList.contains('flatpickr-date')) {
                    flatpickr(element, {
                        dateFormat: "Y-m-d",
                        minDate: "today"
                    });
                } else {
                    flatpickr(element, {
                        enableTime: true,
                        noCalendar: true,
                        dateFormat: "H:i",
                        time_24hr: true
                    });
                }
            });
        });
    </script>
</body>
</html>
