import { apiCall } from "./app/api.js";

// Llamada a la API para obtener los turnos
async function getAllTurnos() {
  try {
    const response = await apiCall("turno/");
    const eventos = response.map((turno) => ({
      title: `${turno.nombrePaciente} ${turno.apellidoPaciente} - ${turno.nombreOdontologo} ${turno.apellidoOdontologo}`,
      start: turno.fechaTurno,
      extendedProps: {
        pacienteNombreApellido:
          turno.nombrePaciente + " " + turno.apellidoPaciente,
        odontologoNombreApellido:
          turno.nombreOdontologo + " " + turno.apellidoOdontologo,
        fechaTurno: turno.fechaTurno,
      },
    }));
    return eventos;
  } catch (error) {
    console.error("Error al obtener los turnos:", error);
    throw error;
  }
}

document.addEventListener("DOMContentLoaded", async function () {
  var calendarEl = document.getElementById("calendar");

  var calendar = new FullCalendar.Calendar(calendarEl, {
    initialView: "dayGridMonth",
    locale: "es", // Establece el idioma en español
    headerToolbar: {
      left: "prev,next today",
      center: "title",
      right: "dayGridMonth,timeGridWeek,timeGridDay", // Elimina timeGridWeek y timeGridDay
    },
    buttonText: {
      today: "Hoy",
      month: "Mes",
      week: "Semana",
      day: "Día",
    },
    eventClick: function (info) {
      // Mostrar el modal al hacer clic en un evento
      $("#eventoModal").modal("show");

      // Llenar el modal con la información del evento
      $("#pacienteNombreInfo").text(
        info.event.extendedProps.pacienteNombreApellido
      );
      $("#odontologoNombreInfo").text(
        info.event.extendedProps.odontologoNombreApellido
      );
      $("#fechaTurnoInfo").text(info.event.extendedProps.fechaTurno);
    },
    events: await getAllTurnos(), // Cargar los eventos desde la API al inicializar el calendario
  });

  calendar.render();
});
