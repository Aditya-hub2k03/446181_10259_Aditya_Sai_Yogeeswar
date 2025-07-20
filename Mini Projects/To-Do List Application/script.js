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

function getNextToDoID() {
  todoCounter = todoCounter + 1;
  return todoCounter;
}

function addToDo() {
  var description = document.getElementById("todoDescription").value;
  var dateTime = document.getElementById("targetDateTime").value;
  var isChecked = document.getElementById("todoStatus").checked;

  var status;
  if (isChecked == true) {
    status = "Done";
  } else {
    status = "Not Done";
  }

  if (description == "" || dateTime == "") {
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

  var finalDateTime = day + ":" + month + ":" + year + " " + minute + ":" + hour;

  var table = document.getElementById("todoTable");
  var tbody = table.getElementsByTagName("tbody")[0];
  var newRow = tbody.insertRow();

  var cell1 = newRow.insertCell(0);
  cell1.innerText = getNextToDoID();

  var cell2 = newRow.insertCell(1);
  cell2.innerText = description;

  var cell3 = newRow.insertCell(2);
  cell3.innerText = finalDateTime;

  var cell4 = newRow.insertCell(3);
  var button = document.createElement("button");
  button.innerText = status;
  button.onclick = function () {
    if (button.innerText == "Done") {
      button.innerText = "Not Done";
    } else {
      button.innerText = "Done";
    }
  };
  cell4.appendChild(button);

  document.getElementById("todoDescription").value = "";
  document.getElementById("targetDateTime").value = "";
  document.getElementById("todoStatus").checked = true;
}
