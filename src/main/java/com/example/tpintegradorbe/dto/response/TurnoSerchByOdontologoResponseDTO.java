package com.example.tpintegradorbe.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TurnoSerchByOdontologoResponseDTO {
    private Long idTurno;
    private String fecha;
    private String dniPaciente;
}
