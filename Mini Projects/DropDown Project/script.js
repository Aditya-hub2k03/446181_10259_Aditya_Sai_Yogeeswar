let allCountries = [];


function populateDropdown(data) {
  const dropdown = document.getElementById("countryDropdown");
  dropdown.innerHTML = "";

  if (data.length === 0) {
    const noOption = document.createElement("option");
    noOption.text = "No matching countries";
    noOption.disabled = true;
    dropdown.appendChild(noOption);
    return;
  }

  const defaultOption = document.createElement("option");
  defaultOption.text = "Select a country";
  defaultOption.disabled = true;
  defaultOption.selected = true;
  dropdown.appendChild(defaultOption);

  data.forEach(country => {
    const option = document.createElement("option");
    option.value = country;
    option.text = country;
    dropdown.appendChild(option);
  });
}

function filterDropdown() {
  const query = document.getElementById("searchBar").value.toLowerCase();
  const filtered = allCountries.filter(c =>
    c.toLowerCase().includes(query)
  );
  populateDropdown(filtered);
}


function loadCountriesFromExcel(filePath) {
  fetch(filePath)
    .then(response => response.arrayBuffer())
    .then(data => {
      const workbook = XLSX.read(data, { type: "array" });
      const sheet = workbook.Sheets[workbook.SheetNames[0]];
      const jsonData = XLSX.utils.sheet_to_json(sheet, { header: 1 });
      allCountries = jsonData.flat().filter(c => typeof c === "string" && c.trim() !== "");
      populateDropdown(allCountries);
    })
    .catch(err => {
      console.error("Failed to load Excel file:", err);
      const dropdown = document.getElementById("countryDropdown");
      dropdown.innerHTML = "<option disabled selected>Failed to load countries</option>";
    });
}


document.addEventListener("DOMContentLoaded", () => {
  loadCountriesFromExcel("countries.xlsx");
  document.getElementById("searchBar").addEventListener("input", filterDropdown);
});
