var todoCounter = 0;

var today = new Date();
var year = today.getFullYear();
var month = today.getMonth() + 1;
if (month < 10) {
  month = "0" + month;
}
var day = today.getDate();
if (day < 10) {
  day = "0" + day;
}
document.getElementById("todayDate").value = year + "-" + month + "-" + day;
var todayDateInput = document.getElementById("todayDate").value;
var now = new Date();
var currentTime = now.getHours().toString().padStart(2, '0') + ":" + now.getMinutes().toString().padStart(2, '0');
document.getElementById("targetDateTime").min = todayDateInput + "T" + currentTime;


var addToDo = {
  getNextToDoID: function() {
    todoCounter += 1;
    return todoCounter;
  },

  run: function() {
    var description = document.getElementById("todoDescription").value;
    var dateTime = document.getElementById("targetDateTime").value;

    var status = "Pending"; 

    if (description === "" || dateTime === "") {
      alert("Please enter all required fields.");
      return;
    }

    var splitParts = dateTime.split("T");
    var datePart = splitParts[0];
    var timePart = splitParts[1];

    var datePieces = datePart.split("-");
    var year = datePieces[0];
    var month = datePieces[1];
    var day = datePieces[2];

    var timePieces = timePart.split(":");
    var hour = timePieces[0];
    var minute = timePieces[1];

    var finalDateTime = day + ":" + month + ":" + year + " " + hour + ":" + minute;

    var table = document.getElementById("todoTable");
    var tbody = table.getElementsByTagName("tbody")[0];
    var newRow = tbody.insertRow();

    var cell1 = newRow.insertCell(0);
    cell1.innerText = this.getNextToDoID();

    var cell2 = newRow.insertCell(1);
    var todayValue = document.getElementById("todayDate").value;
    cell2.innerHTML = description + "<br><small style='color:#4d1775;'>(Added on " + todayValue + ")</small>";

    var cell3 = newRow.insertCell(2);
    cell3.innerText = finalDateTime;

    var cell4 = newRow.insertCell(3);
    var dropdown = document.createElement("select");

    var options = [
      "Pending",
      "To be Started",
      "Half way Completed",
      "Almost Completed",
      "Completed"
    ];

    for (var i = 0; i < options.length; i++) {
      var option = document.createElement("option");
      option.value = options[i];
      option.text = options[i];
      if (options[i] === status) {
        option.selected = true;
      }
      dropdown.appendChild(option);
    }

    cell4.appendChild(dropdown);

    document.getElementById("todoDescription").value = "";
    document.getElementById("targetDateTime").value = "";
  }
};
