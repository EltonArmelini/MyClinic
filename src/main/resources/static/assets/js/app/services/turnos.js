import { apiCall } from "../api.js";
document.addEventListener("DOMContentLoaded", function () {
  const saveButton = document.querySelector("#addTurnoModal .btn-primary");

  saveButton.addEventListener("click", async function () {
    const dniPaciente = document.getElementById("dniPaciente").value;
    const matriculaOdontologo = document.getElementById(
      "matriculaOdontologo"
    ).value;
    const fechaTurno = document.getElementById("fechaTurno").value;

    console.log("DNI Paciente:", dniPaciente);
    console.log("Matrícula Odontólogo:", matriculaOdontologo);
    console.log("Fecha Turno:", fechaTurno);

    try {
      const response = await apiCall("turno/crear", "POST", {
        dniPaciente: dniPaciente,
        matricula: matriculaOdontologo,
        fechaTurno: fechaTurno,
      });
      window.location.reload();
      // Aquí puedes manejar la respuesta del servidor como necesites
    } catch (error) {
      console.error("Error al enviar el turno:", error);
      alert("Error al enviar el turno. Por favor, inténtalo nuevamente.");
    }
  });
  // Evento para el formulario de búsqueda
  document
    .getElementById("searchForm")
    .addEventListener("submit", async function (event) {
      event.preventDefault();
      const searchType = document.getElementById("searchType").value;
      let endpoint = "";

      switch (searchType) {
        case "fecha":
          const fecha = document.getElementById("fechaTurno").value;
          endpoint = `turno/buscar/fecha?fecha=${fecha}`;
          break;
        case "paciente":
          const dniPaciente = document.getElementById("pacienteDni").value;
          endpoint = `turno/buscar/dni?dni=${dniPaciente}`;
          break;
        case "odontologo":
          const matriculaOdontologo = document.getElementById(
            "odontologoMatricula"
          ).value;
          endpoint = `turno/buscar/odontologo/${matriculaOdontologo}`;
          break;
      }

      try {
        const response = await apiCall(endpoint);
        mostrarResultados(response, searchType);
        $("#searchResultsModal").modal("show");
      } catch (error) {
        console.error("Error al realizar la búsqueda:", error);
        window.location.reload();
        alert(
          "Error al realizar la búsqueda. Por favor, inténtalo nuevamente."
        );
      }
    });

  function mostrarResultados(resultados, tipoBusqueda) {
    let resultadosHTML = "";
    if (resultados.length === 0) {
      resultadosHTML = "<p>No se encontraron resultados.</p>";
    } else {
      if (tipoBusqueda === "odontologo") {
        resultadosHTML = `
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th scope="col">ID Turno</th>
                                        <th scope="col">Fecha del Turno</th>
                                        <th scope="col">DNI Paciente</th>
                                    </tr>
                                </thead>
                            <tbody>`;

        resultados.forEach((turno) => {
          resultadosHTML += `<tr>
                                <td>${turno.idTurno}</td>
                                <td>${turno.fecha}</td>
                                <td>${turno.dniPaciente}</td>
                            </tr>`;
        });

        resultadosHTML += "</ul>";
      } else {
        resultadosHTML = `
                    <table class="table">
                        <thead>
                            <tr>
                                <th scope="col">ID Turno</th>
                                <th scope="col">Fecha del Turno</th>
                                <th scope="col">Nombre Paciente</th>
                                <th scope="col">Apellido Paciente</th>
                                <th scope="col">Nombre Odontólogo</th>
                                <th scope="col">Apellido Odontólogo</th>
                            </tr>
                        </thead>
                        <tbody>
                `;

        resultados.forEach((turno) => {
          let fecha = "";
          let dniPaciente = "";
          let matriculaOdontologo = "";

          switch (tipoBusqueda) {
            case "fecha":
              fecha = turno.fechaTurno;
              dniPaciente = turno.dniPaciente;
              matriculaOdontologo = turno.matriculaOdontologo;
              break;
            case "default":
              fecha = turno.fechaTurno;
              dniPaciente = turno.dniPaciente;
              matriculaOdontologo = turno.matriculaOdontologo;
              break;
          }

          resultadosHTML += `
                        <tr>
                            <td>${turno.idTurno}</td>
                            <td>${turno.fechaTurno}</td>
                            <td>${turno.nombrePaciente}</td>
                            <td>${turno.apellidoPaciente}</td>
                            <td>${turno.nombreOdontologo}</td>
                            <td>${turno.apellidoOdontologo}</td>
                        </tr>
                    `;
        });

        resultadosHTML += `
                        </tbody>
                    </table>
                `;
      }
    }
    document.getElementById("searchResultsBody").innerHTML = resultadosHTML;

  }
});
