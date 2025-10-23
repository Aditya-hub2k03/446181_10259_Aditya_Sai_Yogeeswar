var App = {
  data: [],
  history: [],

  loadData: function () {
    fetch("countries.json")
      .then(function (r) { return r.json() })
      .then(function (json) { App.data = json })
  },

  show: function (items) { 
    var box = document.getElementById("results")
    box.innerHTML = "" 
    for (var i = 0; i < items.length; i++) {
      var div = document.createElement("div")
      div.className = "item"
      div.innerText = items[i]
      div.onclick = function (text) {
        return function () {
          App.select(text)
        }
      }(items[i])
      box.appendChild(div)
    }
  },

  select: function (text) {
    document.getElementById("search").value = text
    App.clear()
    if (App.history.indexOf(text) === -1) {
      App.history.push(text)
    }
  },

  clear: function () {
    document.getElementById("results").innerHTML = ""
  },

  search: function (text) {
    if (text === "") {
      if (App.history.length > 0) {
        App.show(App.history.slice().reverse())
      }
      return
    }
    var results = []
    for (var i = 0; i < App.data.length; i++) {
      if (App.data[i].toLowerCase().indexOf(text.toLowerCase()) !== -1) {
        results.push(App.data[i])
      }
    }
    App.show(results)
  },

  start: function () {
    App.loadData()
    var input = document.getElementById("search")
    input.addEventListener("input", function () {
      App.search(input.value)
    })
    input.addEventListener("focus", function () {
      if (input.value === "") {
        App.show(App.history.slice().reverse())
      }
    })
    document.addEventListener("click", function (e) {
      if (!e.target.closest(".wrapper")) {
        App.clear()
      }
    })
  }
}

document.addEventListener("DOMContentLoaded", function () {
  App.start()
})


