import { apiCall } from "../api.js";

document.addEventListener("DOMContentLoaded", async function () {
  const tabla = document.querySelector(".table tbody");

  try {
    const pacientes = await apiCall("paciente");
    pacientes.forEach((paciente) => {
      const fila = document.createElement("tr");
      fila.innerHTML = `
        <td>${paciente.nombrePaciente}</td>
        <td>${paciente.apellidoPaciente}</td>
        <td>${paciente.dniPaciente}</td>
        <td>${paciente.domicilioPaciente}</td>
        <td>${paciente.fechaAlta}</td>
        <td>
          <button type="button" class="btn btn-primary btn-sm btn-edit" data-id="${paciente.id}"><i class='bx bx-edit-alt'></i></button>
          <button type="button" class="btn btn-danger btn-sm btn-delete" data-bs-toggle="modal"
              data-bs-target="#deleteModal" data-id="${paciente.id}"><i class='bx bxs-trash-alt'></i></button>
        </td>
      `;
      tabla.appendChild(fila);
    });
    // Agregar event listener para el botón de agregar nuevo paciente
    const addForm = document.getElementById("addPacienteForm");
    addForm.addEventListener("submit", async (event) => {
      event.preventDefault();
      const nombre = document.getElementById("addName").value;
      const apellido = document.getElementById("addApellido").value;
      const dni = document.getElementById("addDNI").value;
      const domicilio = document.getElementById("addDomicilio").value;
      const fechaAlta = document.getElementById("addFechaAlta").value;
      try {
        await apiCall("paciente/crear", "POST", {
          nombrePaciente: nombre,
          apellidoPaciente: apellido,
          dniPaciente: dni,
          domicilioPaciente: domicilio,
          fechaAlta: fechaAlta,
        });
        alert("Paciente creado exitosamente");
        window.location.reload();
      } catch (error) {
        console.error("Error al crear paciente:", error);
        alert("Error al crear paciente. Por favor, inténtalo nuevamente.");
      }
    });

    const editButtons = document.querySelectorAll(".btn-edit");
    // Agregar un event listener a cada botón de edición
    editButtons.forEach((button) => {
      button.addEventListener("click", function (event) {
        const fila = button.closest("tr"); // Obtener la fila más cercana al botón
        const nombre = fila.querySelector("td:nth-child(1)").innerText; // Obtener el nombre del paciente
        const apellido = fila.querySelector("td:nth-child(2)").innerText; // Obtener el apellido del paciente
        const dni = fila.querySelector("td:nth-child(3)").innerText; // Obtener el DNI del paciente
        const domicilio = fila.querySelector("td:nth-child(4)").innerText; // Obtener el domicilio del paciente
        const fechaAlta = fila.querySelector("td:nth-child(5)").innerText; // Obtener la fecha de alta del paciente

        // Establecer los datos en el formulario de edición
        document.getElementById("editName").value = nombre;
        document.getElementById("editApellido").value = apellido;
        document.getElementById("editDNI").value = dni;
        document.getElementById("editDomicilio").value = domicilio;
        document.getElementById("editFechaAlta").value = fechaAlta;

        // Establecer el ID del paciente en el formulario de edición
        document.getElementById("editForm").dataset.id = button.dataset.id;

        // Mostrar el modal
        const editModal = new bootstrap.Modal(
          document.getElementById("editModal")
        );
        editModal.show();
      });
    });
    // Agregar event listener para el formulario de edición
    const editForm = document.getElementById("editForm");
    editForm.addEventListener("submit", async function (event) {
      event.preventDefault();
      const pacienteId = editForm.dataset.id;
      const nombre = document.getElementById("editName").value;
      const apellido = document.getElementById("editApellido").value;
      const dni = document.getElementById("editDNI").value;
      const domicilio = document.getElementById("editDomicilio").value;
      const fechaAlta = document.getElementById("editFechaAlta").value;

      const pacienteData = {
        id: pacienteId,
        nombrePaciente: nombre,
        apellidoPaciente: apellido,
        dniPaciente: dni,
        domicilioPaciente: domicilio,
        fechaAlta: fechaAlta,
      };

      editForm.reset();
      try {
        // Llamar a la función apiCall para editar el paciente con los datos del paciente
        await apiCall(`paciente/editar/${pacienteId}`, "PUT", pacienteData);

        // Mostrar un mensaje de éxito
        alert("Paciente editado exitosamente");

        // Recargar la página para reflejar los cambios
        window.location.reload();
      } catch (error) {
        console.error("Error al editar paciente:", error);
        alert("Error al editar paciente. Por favor, inténtalo nuevamente.");
      }
    });

    const deleteButtons = document.querySelectorAll(".btn-delete");
    deleteButtons.forEach((button) => {
      button.addEventListener("click", function (event) {
        const pacienteId = button.dataset.id;
        const deleteModal = new bootstrap.Modal(
          document.getElementById("deleteModal")
        );
        const confirmDeleteButton = document.getElementById(
          "confirmDeleteButton"
        );
        confirmDeleteButton.addEventListener("click", async function () {
          deleteModal.hide();
          try {
            await apiCall(`paciente/eliminar/${pacienteId}`, "DELETE");
            alert("Paciente eliminado exitosamente");
            window.location.reload();
          } catch (error) {
            console.error("Error al eliminar paciente:", error);
            alert(
              "Error al eliminar paciente. Por favor, inténtalo nuevamente."
            );
          }
        });
        deleteModal.show();
      });
    });
    const buscarBtn = document.querySelector(".btn-search");
    const dniInput = document.querySelector(".form-control");

    buscarBtn.addEventListener("click", async function () {
      try {
        const dni = dniInput.value.trim();
        if (!dni) {
          alert("Por favor, ingrese un DNI válido.");
          return;
        }
        const paciente = await apiCall(`paciente/buscar?dni=${dni}`);
        console.log(paciente);

        // Mostrar la información del paciente en el modal
        const pacienteInfoModal = document.getElementById("pacienteInfo");
        pacienteInfoModal.innerHTML = `
                <h5>Nombre: ${paciente.nombrePaciente}</h5>
                <h5>Apellido: ${paciente.apellidoPaciente}</h5>
                <h5>DNI: ${paciente.dniPaciente}</h5>
                <h5>Domicilio: ${paciente.domicilioPaciente}</h5>
                <h5>Fecha de Alta: ${paciente.fechaAlta}</h5>
            `;

        // Mostrar el modal
        const pacienteModal = new bootstrap.Modal(
          document.getElementById("pacienteModal")
        );
        pacienteModal.show();
      } catch (error) {
        console.error("Error al buscar paciente:", error);
        alert(
          "Error al buscar paciente. Por favor, inténtalo nuevamente.",
          error
        );
      }
    });
  } catch (error) {
    console.error("Error al cargar los pacientes:", error);
    alert("Error al cargar los pacientes. Por favor, inténtalo nuevamente.");
  }
});
