package com.example.tpintegradorbe.dto.response;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TurnoResponseDTO {

    private Long idTurno;
    private LocalDate fechaTurno;
    private String nombrePaciente;
    private String apellidoPaciente;
    private String nombreOdontologo;
    private String apellidoOdontologo;

}

