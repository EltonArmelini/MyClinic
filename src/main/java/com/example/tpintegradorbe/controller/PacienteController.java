package com.example.tpintegradorbe.controller;



import com.example.tpintegradorbe.dto.request.PacienteRequestDTO;
import com.example.tpintegradorbe.dto.response.PacienteResponseDTO;
import com.example.tpintegradorbe.service.IPacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RestController
@RequestMapping("/paciente")
public class PacienteController {
    private IPacienteService pacienteService;

    @Autowired
    public PacienteController(IPacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping("/crear")
    public ResponseEntity<PacienteResponseDTO> crearPaciente(@RequestBody PacienteRequestDTO pacienteRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteService.crear(pacienteRequestDTO));
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<PacienteResponseDTO> editarPaciente(@PathVariable Long id, @RequestBody PacienteRequestDTO pacienteRequestDTO) {
            return ResponseEntity.ok(pacienteService.editar(id, pacienteRequestDTO));
    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarPaciente(@PathVariable Long id) {
        pacienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/buscar")
    public ResponseEntity<PacienteResponseDTO> buscarPacientePorDni(@RequestParam String dni) {
        return ResponseEntity.ok(pacienteService.buscarPorDni(dni));
    }

    @GetMapping("")
    public ResponseEntity<List<PacienteResponseDTO>> listarTodosPacientes() {
        return ResponseEntity.ok(pacienteService.listarTodos());
    }
}
