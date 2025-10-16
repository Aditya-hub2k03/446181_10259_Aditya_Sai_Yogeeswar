<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Change Password</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .password-container {
            max-width: 500px;
            margin: 0 auto;
            padding: 20px;
            background-color: #f8f9fa;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .password-strength {
            height: 5px;
            background-color: #e9ecef;
            border-radius: 5px;
            margin-top: 5px;
            overflow: hidden;
        }
        .password-strength-fill {
            height: 100%;
            width: 0%;
            transition: width 0.3s, background-color 0.3s;
        }
        .password-requirements {
            font-size: 0.9em;
            color: #6c757d;
            margin-top: 10px;
        }
        .requirement-met {
            color: #198754;
        }
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/views/header.jsp" />

    <div class="container mt-5">
        <div class="password-container">
            <div class="text-center mb-4">
                <i class="fas fa-lock fa-3x text-primary mb-3"></i>
                <h3>Change Password</h3>
                <p class="text-muted">Update your account password</p>
            </div>

            <!-- Display success message if present -->
            <c:if test="${not empty success}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    ${success}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>

            <!-- Display error message if present -->
            <c:if test="${not empty error}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    ${error}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>

            <form action="<c:url value='/user/change-password' />" method="post" id="changePasswordForm">
                <div class="mb-3">
                    <label for="currentPassword" class="form-label">Current Password</label>
                    <div class="input-group">
                        <input type="password" class="form-control" id="currentPassword" name="currentPassword" required>
                        <button class="btn btn-outline-secondary" type="button" id="toggleCurrentPassword">
                            <i class="fas fa-eye"></i>
                        </button>
                    </div>
                </div>

                <div class="mb-3">
                    <label for="newPassword" class="form-label">New Password</label>
                    <div class="input-group">
                        <input type="password" class="form-control" id="newPassword" name="newPassword"
                               pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$"
                               title="Password must contain at least 8 characters, including uppercase, lowercase, number, and special character"
                               required>
                        <button class="btn btn-outline-secondary" type="button" id="toggleNewPassword">
                            <i class="fas fa-eye"></i>
                        </button>
                    </div>
                    <div class="password-strength mt-1">
                        <div class="password-strength-fill" id="passwordStrength"></div>
                    </div>
                    <div class="password-requirements">
                        <p><span id="lengthReq"><i class="fas fa-circle"></i> At least 8 characters</span></p>
                        <p><span id="uppercaseReq"><i class="fas fa-circle"></i> At least one uppercase letter</span></p>
                        <p><span id="lowercaseReq"><i class="fas fa-circle"></i> At least one lowercase letter</span></p>
                        <p><span id="numberReq"><i class="fas fa-circle"></i> At least one number</span></p>
                        <p><span id="specialReq"><i class="fas fa-circle"></i> At least one special character</span></p>
                    </div>
                </div>

                <div class="mb-4">
                    <label for="confirmPassword" class="form-label">Confirm New Password</label>
                    <div class="input-group">
                        <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
                        <button class="btn btn-outline-secondary" type="button" id="toggleConfirmPassword">
                            <i class="fas fa-eye"></i>
                        </button>
                    </div>
                    <div id="passwordMatch" class="mt-1"></div>
                </div>

                <button type="submit" class="btn btn-primary w-100">Change Password</button>
            </form>
        </div>
    </div>

    <jsp:include page="/WEB-INF/views/footer.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // Toggle password visibility
            const toggleCurrentPassword = document.getElementById('toggleCurrentPassword');
            const toggleNewPassword = document.getElementById('toggleNewPassword');
            const toggleConfirmPassword = document.getElementById('toggleConfirmPassword');

            toggleCurrentPassword.addEventListener('click', function() {
                togglePassword('currentPassword', this);
            });

            toggleNewPassword.addEventListener('click', function() {
                togglePassword('newPassword', this);
            });

            toggleConfirmPassword.addEventListener('click', function() {
                togglePassword('confirmPassword', this);
            });

            function togglePassword(fieldId, button) {
                const field = document.getElementById(fieldId);
                if (field.type === 'password') {
                    field.type = 'text';
                    button.innerHTML = '<i class="fas fa-eye-slash"></i>';
                } else {
                    field.type = 'password';
                    button.innerHTML = '<i class="fas fa-eye"></i>';
                }
            }

            // Password strength meter
            const newPassword = document.getElementById('newPassword');
            const passwordStrength = document.getElementById('passwordStrength');
            const lengthReq = document.getElementById('lengthReq');
            const uppercaseReq = document.getElementById('uppercaseReq');
            const lowercaseReq = document.getElementById('lowercaseReq');
            const numberReq = document.getElementById('numberReq');
            const specialReq = document.getElementById('specialReq');
            const confirmPassword = document.getElementById('confirmPassword');
            const passwordMatch = document.getElementById('passwordMatch');
            const form = document.getElementById('changePasswordForm');

            newPassword.addEventListener('input', function() {
                const password = this.value;
                let strength = 0;

                // Check length
                if (password.length >= 8) {
                    strength += 20;
                    lengthReq.classList.add('requirement-met');
                    lengthReq.querySelector('i').classList.replace('fa-circle', 'fa-check-circle');
                } else {
                    lengthReq.classList.remove('requirement-met');
                    lengthReq.querySelector('i').classList.replace('fa-check-circle', 'fa-circle');
                }

                // Check for uppercase
                if (/[A-Z]/.test(password)) {
                    strength += 20;
                    uppercaseReq.classList.add('requirement-met');
                    uppercaseReq.querySelector('i').classList.replace('fa-circle', 'fa-check-circle');
                } else {
                    uppercaseReq.classList.remove('requirement-met');
                    uppercaseReq.querySelector('i').classList.replace('fa-check-circle', 'fa-circle');
                }

                // Check for lowercase
                if (/[a-z]/.test(password)) {
                    strength += 20;
                    lowercaseReq.classList.add('requirement-met');
                    lowercaseReq.querySelector('i').classList.replace('fa-circle', 'fa-check-circle');
                } else {
                    lowercaseReq.classList.remove('requirement-met');
                    lowercaseReq.querySelector('i').classList.replace('fa-check-circle', 'fa-circle');
                }

                // Check for numbers
                if (/\d/.test(password)) {
                    strength += 20;
                    numberReq.classList.add('requirement-met');
                    numberReq.querySelector('i').classList.replace('fa-circle', 'fa-check-circle');
                } else {
                    numberReq.classList.remove('requirement-met');
                    numberReq.querySelector('i').classList.replace('fa-check-circle', 'fa-circle');
                }

                // Check for special characters
                if (/[@$!%*?&]/.test(password)) {
                    strength += 20;
                    specialReq.classList.add('requirement-met');
                    specialReq.querySelector('i').classList.replace('fa-circle', 'fa-check-circle');
                } else {
                    specialReq.classList.remove('requirement-met');
                    specialReq.querySelector('i').classList.replace('fa-check-circle', 'fa-circle');
                }

                // Update strength meter
                passwordStrength.style.width = strength + '%';

                // Set color based on strength
                if (strength < 40) {
                    passwordStrength.style.backgroundColor = '#dc3545'; // Red
                } else if (strength < 80) {
                    passwordStrength.style.backgroundColor = '#ffc107'; // Yellow
                } else {
                    passwordStrength.style.backgroundColor = '#198754'; // Green
                }
            });

            // Check if passwords match
            confirmPassword.addEventListener('input', function() {
                if (this.value === newPassword.value && this.value !== '') {
                    passwordMatch.innerHTML = '<i class="fas fa-check-circle text-success me-1"></i><span class="text-success">Passwords match</span>';
                } else {
                    passwordMatch.innerHTML = '<i class="fas fa-times-circle text-danger me-1"></i><span class="text-danger">Passwords do not match</span>';
                }
            });

            // Form validation
            form.addEventListener('submit', function(e) {
                if (newPassword.value !== confirmPassword.value) {
                    e.preventDefault();
                    alert('New password and confirm password do not match!');
                }
            });
        });
    </script>
</body>
</html>
