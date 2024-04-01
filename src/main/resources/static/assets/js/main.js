document.addEventListener("DOMContentLoaded", function (event) {
  const showNavbar = (toggleId, navId, bodyId, headerId) => {
    const toggle = document.getElementById(toggleId),
      nav = document.getElementById(navId),
      bodypd = document.getElementById(bodyId),
      headerpd = document.getElementById(headerId);

    // Validate that all variables exist
    if (toggle && nav && bodypd && headerpd) {
      toggle.addEventListener("click", () => {
        // show navbar
        nav.classList.toggle("showSidebar");
        // change icon
        toggle.classList.toggle("bx-x");
        // add padding to body
        bodypd.classList.toggle("body-pd");
        // add padding to header
        headerpd.classList.toggle("body-pd");
      });
    }
  };

  showNavbar("header-toggle", "nav-bar", "body-pd", "header");

  /*===== LINK ACTIVE =====*/
  const linkColor = document.querySelectorAll(".nav_link");

  function colorLink() {
    if (linkColor) {
      linkColor.forEach((l) => l.classList.remove("active"));
      this.classList.add("active");
    }
  }
  linkColor.forEach((l) => l.addEventListener("click", colorLink));

  const searchForm = document.getElementById('searchForm');
  const searchFieldContainer = document.getElementById('searchFieldContainer');

  document.getElementById('searchType').addEventListener('change', function () {
      const searchType = this.value;
      let searchFieldHTML = '';

      switch (searchType) {
          case 'fecha':
              searchFieldHTML = `<div class="mb-3">
                                      <label for="fechaTurno" class="form-label">Buscar por Fecha del Turno</label>
                                      <input type="date" class="form-control" id="fechaTurno">
                                  </div>`;
              break;
          case 'paciente':
              searchFieldHTML = `<div class="mb-3">
                                      <label for="pacienteDni" class="form-label">Buscar por Paciente (DNI)</label>
                                      <input type="text" class="form-control" id="pacienteDni">
                                  </div>`;
              break;
          case 'odontologo':
              searchFieldHTML = `<div class="mb-3">
                                      <label for="odontologoMatricula" class="form-label">Buscar por Odontólogo (Matrícula)</label>
                                      <input type="text" class="form-control" id="odontologoMatricula">
                                  </div>`;
              break;
      }

      searchFieldContainer.innerHTML = searchFieldHTML;
  });

  searchForm.addEventListener('submit', function (event) {
      event.preventDefault();
      const searchResultsModal = new bootstrap.Modal(document.getElementById('searchResultsModal'));
      searchResultsModal.show();
  });

});
