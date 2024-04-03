package com.example.tpintegradorbe.entity;


import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

@Entity
@Table(name="turnos")
@AllArgsConstructor @NoArgsConstructor
@Getter
@Setter
@ToString
public class Turno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTurno;

    @NotNull
    @ManyToOne
    private Paciente paciente;

    @NotNull
    @ManyToOne
    private Odontologo odontologo;

    @NotNull
    private LocalDate fechaTurno;


}
