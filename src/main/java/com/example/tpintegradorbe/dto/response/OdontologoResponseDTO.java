package com.example.tpintegradorbe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OdontologoResponseDTO {
    private Long id;
    private String nombreOdontologo;
    private String apellidoOdontologo;
    private String matricula;
}