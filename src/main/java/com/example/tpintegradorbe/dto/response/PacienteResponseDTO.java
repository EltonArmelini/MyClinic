package com.example.tpintegradorbe.dto.response;


import lombok.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PacienteResponseDTO {
    private Long id;
    private String nombrePaciente;
    private String apellidoPaciente;
    private String domicilioPaciente;
    private String dniPaciente;
    private LocalDate fechaAlta;

}
