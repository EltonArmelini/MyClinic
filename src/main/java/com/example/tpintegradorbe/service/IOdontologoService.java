package com.example.tpintegradorbe.service;


import com.example.tpintegradorbe.dto.request.OdontologoRequestDTO;
import com.example.tpintegradorbe.dto.response.OdontologoResponseDTO;

import java.util.List;

public interface IOdontologoService {
    OdontologoResponseDTO crear(OdontologoRequestDTO odontologo);
    OdontologoResponseDTO editar(Long id, OdontologoRequestDTO odontologo);
    void eliminar(Long id);
    OdontologoResponseDTO buscarPorMatricula(String matricula);
    List<OdontologoResponseDTO> listarTodos();
}
