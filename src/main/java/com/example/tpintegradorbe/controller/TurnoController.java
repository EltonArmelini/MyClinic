package com.example.tpintegradorbe.controller;


import com.example.tpintegradorbe.dto.request.TurnoEditRequestDTO;
import com.example.tpintegradorbe.dto.request.TurnoRequestDTO;
import com.example.tpintegradorbe.dto.response.TurnoResponseDTO;
import com.example.tpintegradorbe.dto.response.TurnoSerchByOdontologoResponseDTO;
import com.example.tpintegradorbe.service.ITurnoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RestController
@RequestMapping("/turno")
public class TurnoController {

    private ITurnoService turnoService;
    private ModelMapper modelMapper;

    @Autowired
    public TurnoController(ITurnoService turnoService, ModelMapper modelMapper) {
        this.turnoService = turnoService;
        this.modelMapper = modelMapper;
    }

    // GET -> Busca por id de turno
    @GetMapping("/buscar/{id}")
    public ResponseEntity<TurnoResponseDTO> buscarTurnoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(turnoService.buscarTurnoPorId(id));
    }
    // GET -> Busca por paciente dni
    @GetMapping("/buscar/dni")
    public ResponseEntity<List<TurnoResponseDTO>> buscarTurnosPorDniPaciente(@RequestParam String dni) {
        return ResponseEntity.ok(turnoService.buscarTurnosPorDniPaciente(dni));
    }

    // GET -> Busca por fecha
    @GetMapping("/buscar/fecha")
    public ResponseEntity<List<TurnoResponseDTO>> buscarTurnosPorFecha(@RequestParam LocalDate fecha) {
        return ResponseEntity.ok(turnoService.buscarTurnosPorFecha(fecha));
    }
    // GET -> Busca por id de odontólogo
    @GetMapping("/buscar/odontologo/{matricula}")
    public ResponseEntity<List<TurnoSerchByOdontologoResponseDTO>> buscarTurnosPorIdOdontologo(@PathVariable String matricula) {
        return ResponseEntity.ok(turnoService.buscarTurnosPorOdontologo(matricula));
    }

    // GET -> Muestra todos
    @GetMapping("/")
    public ResponseEntity<List<TurnoResponseDTO>> listarTodosTurnos() {
        return ResponseEntity.ok(turnoService.listarTodosLosTurnos());
    }

    // POST -> Crea nuevo turno
    @PostMapping("/crear")
    public ResponseEntity<TurnoResponseDTO> crearTurno(@RequestBody TurnoRequestDTO turnoRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(turnoService.crearTurno(turnoRequestDTO));
    }

    // PUT -> Actualiza un turno existente
    @PutMapping("/editar")
    public ResponseEntity<TurnoResponseDTO> editarTurno(@RequestBody TurnoEditRequestDTO turnoEditRequestDTO) {
        return ResponseEntity.ok(turnoService.editarTurno(turnoEditRequestDTO));
    }

    // DELETE -> Elimina por id a un turno
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarTurno(@PathVariable Long id) {
        turnoService.eliminarTurno(id);
        return ResponseEntity.noContent().build();
    }
}
