package com.example.tpintegradorbe.dto.request;

import lombok.*;

import java.time.LocalDate;
@AllArgsConstructor @NoArgsConstructor
@Getter
@Setter
@ToString
public class TurnoRequestDTO {
    private String matricula;
    private String dniPaciente;
    private LocalDate fechaTurno;
}
