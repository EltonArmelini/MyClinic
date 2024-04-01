package com.example.tpintegradorbe.service;


import com.example.tpintegradorbe.dto.request.PacienteRequestDTO;
import com.example.tpintegradorbe.dto.response.PacienteResponseDTO;
import com.example.tpintegradorbe.entity.Paciente;

import java.util.List;

public interface IPacienteService {
    PacienteResponseDTO crear(PacienteRequestDTO paciente);
    PacienteResponseDTO editar(Long id, PacienteRequestDTO Paciente);
    void eliminar(Long id);
    PacienteResponseDTO buscarPorDni(String dni);
    List<PacienteResponseDTO> listarTodos();

}
