var todoCounter = 0;
var todos = [];
var editingTodoId = null;
var pendingPastDateTask = null;
var sortConfig = {
    id: { ascending: true },
    dateTime: { ascending: true },
    status: { order: ["In progress", "Pending", "completed"] },
};

function initializeForm() {
    var today = new Date();
    var yyyy = today.getFullYear();
    var mm = String(today.getMonth() + 1).padStart(2, "0");
    var dd = String(today.getDate()).padStart(2, "0");
    var formattedDate = yyyy + "-" + mm + "-" + dd;
    document.getElementById("todayDate").value = formattedDate;
    document.getElementById("status").value = "Pending";
}

window.onload = function () {
    initializeForm();
    setupModalEvents();
    flatpickr("#targetDateTime", { enableTime: true, dateFormat: "Y-m-d\\TH:i" });
    flatpickr("#globalStartDate", { enableTime: true, dateFormat: "Y-m-d\\TH:i" });
    flatpickr("#globalEndDate", { enableTime: true, dateFormat: "Y-m-d\\TH:i" });
    fetchTodos();
};

function setupModalEvents() {
    const modal = document.getElementById("pastDateModal");
    const yesBtn = document.getElementById("confirmYes");
    const noBtn = document.getElementById("confirmNo");
    const statusCheckboxes = ["pendingStatus", "inProgressStatus", "completedStatus"];

    yesBtn.addEventListener("click", function () {
        statusCheckboxes.forEach((id) => {
            document.getElementById(id).disabled = false;
        });
    });

    noBtn.addEventListener("click", function () {
        modal.style.display = "none";
        pendingPastDateTask = null;
        resetStatusCheckboxes();
    });

    statusCheckboxes.forEach((id) => {
        document.getElementById(id).addEventListener("change", function () {
            if (this.checked) {
                statusCheckboxes.forEach((otherId) => {
                    if (otherId !== id) {
                        document.getElementById(otherId).checked = false;
                    }
                });
                let selectedStatus = "";
                if (id === "pendingStatus") selectedStatus = "Pending";
                else if (id === "inProgressStatus") selectedStatus = "In progress";
                else if (id === "completedStatus") selectedStatus = "completed";
                addPastDateTask(selectedStatus);
                modal.style.display = "none";
                resetStatusCheckboxes();
            }
        });
    });
}

function resetStatusCheckboxes() {
    const statusCheckboxes = ["pendingStatus", "inProgressStatus", "completedStatus"];
    statusCheckboxes.forEach((id) => {
        const checkbox = document.getElementById(id);
        checkbox.checked = false;
        checkbox.disabled = true;
    });
}

function getNextToDoID() {
    todoCounter += 1;
    return todoCounter;
}

function formatDateTimeDisplay(dateTime) {
    var parts = dateTime.split("T");
    if (parts.length < 2) return dateTime;
    var datePart = parts[0], timePart = parts[1];
    var dparts = datePart.split("-");
    var year = dparts[0], month = dparts[1], day = dparts[2];
    var tparts = timePart.split(":");
    var hour = tparts[0], min = tparts[1];
    return day + ":" + month + ":" + year + " " + hour + ":" + min;
}

function formatEditedDateDisplay(dateObj) {
    var hh = String(dateObj.getHours()).padStart(2, "0");
    var mm = String(dateObj.getMinutes()).padStart(2, "0");
    var dd = String(dateObj.getDate()).padStart(2, "0");
    var MM = String(dateObj.getMonth() + 1).padStart(2, "0");
    var yyyy = dateObj.getFullYear();
    return hh + ":" + mm + " " + dd + "-" + MM + "-" + yyyy;
}

function sortById() {
    sortConfig.id.ascending = !sortConfig.id.ascending;
    sortTodos();
    renderTable();
}

function sortByDateTime() {
    sortConfig.dateTime.ascending = !sortConfig.dateTime.ascending;
    sortTodos();
    renderTable();
}

function sortByStatus() {
    const currentOrder = sortConfig.status.order;
    if (currentOrder[0] === "In progress" && currentOrder[1] === "Pending") {
        sortConfig.status.order = ["Pending", "completed", "In progress"];
    } else if (currentOrder[0] === "Pending" && currentOrder[1] === "completed") {
        sortConfig.status.order = ["completed", "In progress", "Pending"];
    } else {
        sortConfig.status.order = ["In progress", "Pending", "completed"];
    }
    sortTodos();
    renderTable();
}

function sortTodos() {
    if (sortConfig.id.ascending) {
        todos.sort((a, b) => a.id - b.id);
    } else {
        todos.sort((a, b) => b.id - a.id);
    }
    if (sortConfig.dateTime.ascending) {
        todos.sort((a, b) => new Date(a.targetDateTime) - new Date(b.targetDateTime));
    } else {
        todos.sort((a, b) => new Date(b.targetDateTime) - new Date(a.targetDateTime));
    }
    const statusOrder = sortConfig.status.order;
    todos.sort((a, b) => {
        const statusA = a.status.startsWith("In progress") ? "In progress" :
                        a.status.startsWith("completed") ? "completed" : "Pending";
        const statusB = b.status.startsWith("In progress") ? "In progress" :
                        b.status.startsWith("completed") ? "completed" : "Pending";
        return statusOrder.indexOf(statusA) - statusOrder.indexOf(statusB);
    });
}

function addToDo() {
    var description = document.getElementById("todoDescription").value.trim();
    var dateTime = document.getElementById("targetDateTime").value;
    var addedDate = document.getElementById("todayDate").value;

    if (!description || !dateTime) {
        alert("Please enter all required fields.");
        return;
    }

    var nowISO = new Date().toISOString().slice(0, 16);
    if (dateTime < nowISO) {
        pendingPastDateTask = {
            description: description,
            addedDate: addedDate,
            targetDateTime: dateTime,
        };
        document.getElementById("pastDateModal").style.display = "block";
        return;
    }

    var newId = getNextToDoID();
    var todo = {
        id: newId,
        description: description,
        addedDate: addedDate,
        editedDate: null,
        targetDateTime: dateTime,
        status: "Pending",
    };

    fetch("TodoServlet", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(todo),
    })
    .then(response => response.json())
    .then(data => {
        todos.push(data);
        sortTodos();
        renderTable();
        resetForm();
    })
    .catch(error => console.error("Error:", error));
}

function addPastDateTask(selectedStatus) {
    if (!pendingPastDateTask) return;

    var newId = getNextToDoID();
    var todo = {
        id: newId,
        description: pendingPastDateTask.description,
        addedDate: pendingPastDateTask.addedDate,
        editedDate: null,
        targetDateTime: pendingPastDateTask.targetDateTime,
        status: selectedStatus,
    };

    fetch("TodoServlet", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(todo),
    })
    .then(response => response.json())
    .then(data => {
        todos.push(data);
        sortTodos();
        renderTable();
        resetForm();
        pendingPastDateTask = null;
    })
    .catch(error => console.error("Error:", error));
}

function fetchTodos() {
    fetch("TodoServlet")
        .then(response => response.json())
        .then(data => {
            todos = data;
            sortTodos();
            renderTable();
        })
        .catch(error => console.error("Error:", error));
}

function renderTable() {
    var tbody = document.getElementById("todoTable").getElementsByTagName("tbody")[0];
    tbody.innerHTML = "";
    todos.forEach(function (todo) {
        var row = tbody.insertRow();
        var cellId = row.insertCell(0);
        cellId.innerText = todo.id;

        var cellDesc = row.insertCell(1);
        var addedText = "(Added on " + todo.addedDate + ")";
        if (todo.editedDate) {
            addedText += ", (Edited on " + formatEditedDateDisplay(new Date(todo.editedDate)) + ")";
        }
        cellDesc.innerHTML = todo.description + '<br><small style="color:gray;">' + addedText + "</small>";

        var cellTarget = row.insertCell(2);
        cellTarget.innerText = formatDateTimeDisplay(todo.targetDateTime);

        var cellStatus = row.insertCell(3);
        cellStatus.innerText = todo.status.toLowerCase();
        cellStatus.style.textTransform = "capitalize";
        cellStatus.style.textAlign = "center";

        var cellAction = row.insertCell(4);
        var actionBtn = document.createElement("button");
        actionBtn.innerText = todo.status === "Pending" ? "In Progress" :
                              todo.status.startsWith("In progress") ? "Completed" : "";
        actionBtn.style.cursor = "pointer";

        if (todo.status === "completed") {
            actionBtn.disabled = true;
            actionBtn.style.display = "none";
        }

        var dateTimeInput = document.createElement("input");
        dateTimeInput.type = "text";
        dateTimeInput.className = "status-date-time";
        dateTimeInput.style.display = "none";
        dateTimeInput.placeholder = "Select Date and Time";

        actionBtn.addEventListener("click", function () {
            if (todo.status === "Pending") {
                todo.status = "In progress";
                cellStatus.innerText = "In progress";
                actionBtn.innerText = "Completed";
                dateTimeInput.style.display = "block";
                flatpickr(dateTimeInput, {
                    enableTime: true,
                    dateFormat: "Y-m-d\\TH:i",
                    onClose: function (selectedDates) {
                        if (selectedDates.length > 0) {
                            var selectedDateTime = selectedDates[0];
                            todo.status = "In progress (Started on " + formatEditedDateDisplay(selectedDateTime) + ")";
                            cellStatus.innerText = todo.status;
                            dateTimeInput.style.display = "none";
                            updateTodo(todo);
                        }
                    },
                });
            } else if (todo.status.startsWith("In progress")) {
                todo.status = "completed";
                dateTimeInput.style.display = "block";
                flatpickr(dateTimeInput, {
                    enableTime: true,
                    dateFormat: "Y-m-d\\TH:i",
                    onClose: function (selectedDates) {
                        if (selectedDates.length > 0) {
                            var selectedDateTime = selectedDates[0];
                            todo.status = "completed (Completed on " + formatEditedDateDisplay(selectedDateTime) + ")";
                            cellStatus.innerText = todo.status;
                            dateTimeInput.style.display = "none";
                            actionBtn.disabled = true;
                            actionBtn.style.display = "none";
                            cellEdit.innerHTML = "";
                            updateTodo(todo);
                        }
                    },
                });
            }
        });

        cellAction.appendChild(actionBtn);
        cellAction.appendChild(dateTimeInput);

        var cellEdit = row.insertCell(5);
        if (!todo.status.startsWith("completed")) {
            var editBtn = document.createElement("button");
            editBtn.innerText = "Edit";
            editBtn.style.cursor = "pointer";
            editBtn.onclick = function () {
                enterEditMode(todo.id);
            };
            cellEdit.appendChild(editBtn);
        }
    });
}

function enterEditMode(id) {
    var todo = todos.find((t) => t.id === id);
    if (!todo) return;

    editingTodoId = id;
    document.getElementById("todoDescription").value = todo.description;
    document.getElementById("targetDateTime").value = todo.targetDateTime;
    document.getElementById("todayDate").value = todo.addedDate;
    document.getElementById("status").value = todo.status;
    document.getElementById("addButton").style.display = "none";
    document.getElementById("updateButton").style.display = "inline";
    document.getElementById("cancelButton").style.display = "inline";
}

function cancelEdit() {
    editingTodoId = null;
    resetForm();
    document.getElementById("addButton").style.display = "inline";
    document.getElementById("updateButton").style.display = "none";
    document.getElementById("cancelButton").style.display = "none";
}

function updateToDo() {
    if (editingTodoId === null) return;

    var description = document.getElementById("todoDescription").value.trim();
    var dateTime = document.getElementById("targetDateTime").value;
    var status = document.getElementById("status").value;
    var addedDate = document.getElementById("todayDate").value;

    if (!description || !dateTime) {
        alert("Please enter all required fields.");
        return;
    }

    var nowISO = new Date().toISOString().slice(0, 16);
    if (dateTime < nowISO) {
        alert("You cannot select a past date and time for updates!");
        return;
    }

    var todo = todos.find((t) => t.id === editingTodoId);
    if (!todo) return;

    todo.description = description;
    todo.targetDateTime = dateTime;
    todo.status = status;
    todo.editedDate = new Date().toISOString();

    updateTodo(todo);
    cancelEdit();
}

function updateTodo(todo) {
    fetch("TodoServlet", {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(todo),
    })
    .then(response => response.json())
    .then(data => {
        sortTodos();
        renderTable();
    })
    .catch(error => console.error("Error:", error));
}

function resetForm() {
    document.getElementById("todoForm").reset();
    initializeForm();
}

function globalSearch() {
    const keywordInput = document.getElementById("globalSearchKeyword").value.toLowerCase().trim();
    const startDateInput = document.getElementById("globalStartDate").value;
    const endDateInput = document.getElementById("globalEndDate").value;
    const tbody = document.getElementById("todoTable").getElementsByTagName("tbody")[0];
    const rows = tbody.getElementsByTagName("tr");

    for (let row of rows) {
        const cells = row.getElementsByTagName("td");
        const idText = cells[0].innerText.toLowerCase();
        const descText = cells[1].innerText.toLowerCase();
        const statusText = cells[3].innerText.toLowerCase();
        const todoId = parseInt(cells[0].innerText);
        const todo = todos.find((t) => t.id === todoId);
        const todoDateTime = todo ? todo.targetDateTime : "";

        let matchKeyword = true;
        if (keywordInput) {
            matchKeyword = idText.includes(keywordInput) ||
                           descText.includes(keywordInput) ||
                           statusText.includes(keywordInput);
        }

        let matchDateTime = true;
        if (startDateInput || endDateInput) {
            const todoDate = new Date(todoDateTime);
            const startDate = startDateInput ? new Date(startDateInput) : null;
            const endDate = endDateInput ? new Date(endDateInput) : null;

            if (startDate && endDate) {
                matchDateTime = todoDate >= startDate && todoDate <= endDate;
            } else if (startDate) {
                matchDateTime = todoDate >= startDate;
            } else if (endDate) {
                matchDateTime = todoDate <= endDate;
            }
        }

        row.style.display = matchKeyword && matchDateTime ? "" : "none";
    }
}

function resetGlobalFilters() {
    document.getElementById("globalSearchKeyword").value = "";
    document.getElementById("globalStartDate").value = "";
    document.getElementById("globalEndDate").value = "";
    globalSearch();
}
