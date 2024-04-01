package com.example.tpintegradorbe.service;


import com.example.tpintegradorbe.dto.request.TurnoEditRequestDTO;
import com.example.tpintegradorbe.dto.request.TurnoRequestDTO;
import com.example.tpintegradorbe.dto.response.TurnoResponseDTO;
import com.example.tpintegradorbe.dto.response.TurnoSerchByOdontologoResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface ITurnoService {
    TurnoResponseDTO crearTurno(TurnoRequestDTO turno);
    TurnoResponseDTO editarTurno(TurnoEditRequestDTO turno);
    void eliminarTurno(Long id);
    TurnoResponseDTO buscarTurnoPorId(Long id);
    List<TurnoResponseDTO> listarTodosLosTurnos();
    List<TurnoSerchByOdontologoResponseDTO> buscarTurnosPorOdontologo(String matricula);
    List<TurnoResponseDTO> buscarTurnosPorFecha(LocalDate fecha);
    List<TurnoResponseDTO> buscarTurnosPorDniPaciente(String dniPaciente);
}
