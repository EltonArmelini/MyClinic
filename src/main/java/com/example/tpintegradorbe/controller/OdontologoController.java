package com.example.tpintegradorbe.controller;


import com.example.tpintegradorbe.dto.request.OdontologoRequestDTO;
import com.example.tpintegradorbe.dto.response.OdontologoResponseDTO;
import com.example.tpintegradorbe.entity.Odontologo;
import com.example.tpintegradorbe.service.IOdontologoService;
import com.example.tpintegradorbe.service.implementation.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/odontologo")
public class OdontologoController {

    private IOdontologoService odontologoService;

    @Autowired
    public OdontologoController(IOdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    @PostMapping("/crear")
    public ResponseEntity<OdontologoResponseDTO> crearOdontologo(@RequestBody OdontologoRequestDTO odontologoRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(odontologoService.crear(odontologoRequestDTO));
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<OdontologoResponseDTO> editarOdontologo(@PathVariable Long id, @RequestBody OdontologoRequestDTO odontologoRequestDTO) {
        return ResponseEntity.ok(odontologoService.editar(id, odontologoRequestDTO));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarOdontologo(@PathVariable Long id) {
        odontologoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar/{matricula}")
    public ResponseEntity<OdontologoResponseDTO> buscarOdontologoPorMatricula(@PathVariable String matricula) {
        return ResponseEntity.ok(odontologoService.buscarPorMatricula(matricula));
    }

    @GetMapping("/")
    public ResponseEntity<List<OdontologoResponseDTO>> listarTodosOdontologos() {
        return ResponseEntity.ok(odontologoService.listarTodos());
    }
}

