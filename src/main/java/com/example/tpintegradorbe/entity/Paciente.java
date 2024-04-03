package com.example.tpintegradorbe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pacientes")
@AllArgsConstructor @NoArgsConstructor
@Getter
@Setter
@ToString
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nombrePaciente;

    @NotNull
    private String apellidoPaciente;

    @NotNull
    private String domicilioPaciente;

    @Column(unique = true)
    private String dniPaciente;

    @NotNull
    private LocalDate fechaAlta;

    @OneToMany(mappedBy = "paciente")
    @JsonIgnore
    private Set<Turno> turnoSet = new HashSet<>();

}
