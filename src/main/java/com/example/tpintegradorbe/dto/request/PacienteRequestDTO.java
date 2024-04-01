package com.example.tpintegradorbe.dto.request;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PacienteRequestDTO {
    private String nombrePaciente;
    private String apellidoPaciente;
    private String domicilioPaciente;
    private String dniPaciente;
    private LocalDate fechaAlta;
}
