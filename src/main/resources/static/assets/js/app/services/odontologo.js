import { apiCall } from "../api.js";

document.addEventListener("DOMContentLoaded", async function () {
  const tabla = document.querySelector(".table tbody");

  // Función para obtener la lista de odontólogos y renderizarla en la tabla
  async function getOdontologos() {
    try {
      const odontologos = await apiCall("odontologo/");
      tabla.innerHTML = ""; // Limpiar la tabla antes de renderizar los datos nuevos
      odontologos.forEach((odontologo) => {
        const fila = document.createElement("tr");
        fila.innerHTML = `
            <td>${odontologo.nombreOdontologo}</td>
            <td>${odontologo.apellidoOdontologo}</td>
            <td>${odontologo.matricula}</td>
            <td>
              <button type="button" class="btn btn-primary btn-sm btn-edit" data-bs-toggle="modal"
                  data-bs-target="#editOdontologoModal" data-id="${odontologo.id}">
                  <i class='bx bx-edit-alt'></i>
              </button>
              <button type="button" class="btn btn-danger btn-sm btn-delete" data-bs-toggle="modal"
                  data-bs-target="#deleteOdontologoModal" data-id="${odontologo.id}">
                  <i class='bx bxs-trash-alt'></i>
              </button>
            </td>
          `;
        tabla.appendChild(fila);
      });
      editOdontologos();
      deleteOdontologos();
    } catch (error) {
      console.error("Error al cargar los odontólogos:", error);
      alert(
        "Error al cargar los odontólogos. Por favor, inténtalo nuevamente."
      );
    }
  }

  // Llamar a la función getOdontologos al cargar la página para mostrar los odontólogos
  getOdontologos();

  // Agregar event listener para el botón de eliminar odontólogo
  function deleteOdontologos() {
    document
      .getElementById("deleteOdontologoModal")
      .addEventListener("show.bs.modal", function (event) {
        const button = event.relatedTarget;
        const id = button.dataset.id;

        document
          .getElementById("confirmDeleteButton")
          .addEventListener("click", async function () {
            try {
              await apiCall(`odontologo/eliminar/${id}`, "DELETE");
              alert("Odontólogo eliminado exitosamente");
              getOdontologos();
              const modal = bootstrap.Modal.getInstance(
                document.getElementById("deleteOdontologoModal")
              );
              modal.hide();
            } catch (error) {
              console.error("Error al eliminar odontólogo:", error);
              alert(
                "Error al eliminar odontólogo. Por favor, inténtalo nuevamente."
              );
            }
          });
      });
  }

  // Agregar un event listener a cada botón de edición
  function editOdontologos() {
    const editButtons = document.querySelectorAll(".btn-edit");
    editButtons.forEach((button) => {
      button.addEventListener("click", function (event) {
        const fila = button.closest("tr"); // Obtener la fila más cercana al botón
        const nombreFila = fila.querySelector("td:nth-child(1)").innerText; // Obtener el nombre del odontólogo
        const apellidoFila = fila.querySelector("td:nth-child(2)").innerText; // Obtener el apellido del odontólogo
        const matriculaFila = fila.querySelector("td:nth-child(3)").innerText; // Obtener la matricula del odontólogo
        const idFila = button.dataset.id; // Obtener el ID del odontólogo

        // Establecer los datos en el formulario de edición
        document.getElementById("editNombre").value = nombreFila;
        document.getElementById("editApellido").value = apellidoFila;
        document.getElementById("editMatricula").value = matriculaFila;
        // Establecer el ID del odontólogo en el formulario de edición
        document.getElementById("editOdontologoModal").dataset.id = idFila;
        console.log(fila);
        console.log(nombreFila, apellidoFila, matriculaFila, idFila);
      });
    });

    const editForm = document.getElementById("editOdontologoForm");
    editForm.addEventListener("submit", async function (event) {
      event.preventDefault();
      const odontologoId = document.getElementById("editOdontologoModal")
        .dataset.id;
      const nombre = document.getElementById("editNombre").value;
      const apellido = document.getElementById("editApellido").value;
      const matricula = document.getElementById("editMatricula").value;

      const odontologoData = {
        id: odontologoId,
        nombreOdontologo: nombre,
        apellidoOdontologo: apellido,
        matricula: matricula,
      };

      editForm.reset();
      console.log(odontologoData);
      try {
        // Llamar a la función apiCall para editar el odontólogo con los datos del formulario
        await apiCall(
          `odontologo/editar/${odontologoId}`,
          "PUT",
          odontologoData
        );

        // Mostrar un mensaje de éxito
        alert("Odontólogo editado exitosamente");

        // Recargar la página para reflejar los cambios
        window.location.reload();
      } catch (error) {
        console.error("Error al editar odontólogo:", error);
        alert("Error al editar odontólogo. Por favor, inténtalo nuevamente.");
      }
    });
  }
  // Agregar event listener para el formulario de agregar odontólogo
  const addForm = document.getElementById("addOdontologoForm");
  addForm.addEventListener("submit", async (event) => {
    event.preventDefault();
    const nombre = document.getElementById("addNombre").value;
    const apellido = document.getElementById("addApellido").value;
    const matricula = document.getElementById("addMatricula").value;
    try {
      await apiCall("odontologo/crear", "POST", {
        nombreOdontologo: nombre,
        apellidoOdontologo: apellido,
        matricula: matricula,
      });
      alert("Odontólogo creado exitosamente");
      window.location.reload();
    } catch (error) {
      console.error("Error al crear odontólogo:", error);
      alert("Error al crear odontólogo. Por favor, inténtalo nuevamente.");
      // Aplicar estilo de focus rojo a todos los campos del formulario
      document.getElementById("addNombre").classList.add("is-invalid");
      document.getElementById("addApellido").classList.add("is-invalid");
      document.getElementById("addMatricula").classList.add("is-invalid");
    }
  });

  const buscarBtn = document.querySelector(".btn-search");
  const matriculaInput = document.getElementById("searchInput");

  buscarBtn.addEventListener("click", async function () {
    try {
      const matricula = matriculaInput.value.trim();
      if (!matricula) {
        alert("Por favor, ingrese una matrícula válida.");
        return;
      }
      const odontologo = await apiCall(
        `odontologo/buscar/${matricula}`
      );
      console.log(odontologo);

      // Mostrar la información del odontólogo en el modal
      const odontologoInfoModal = document.getElementById("odontologoInfo");
      odontologoInfoModal.innerHTML = `
            <h5>Nombre: ${odontologo.nombreOdontologo}</h5>
            <h5>Apellido: ${odontologo.apellidoOdontologo}</h5>
            <h5>Matrícula: ${odontologo.matricula}</h5>
        `;

      // Mostrar el modal
      const odontologoModal = new bootstrap.Modal(
        document.getElementById("odontologoModal")
      );
      odontologoModal.show();
    } catch (error) {
      console.error("Error al buscar odontólogo:", error);
      alert("Error al buscar odontólogo. Por favor, inténtalo nuevamente.");
    }
  });
});
