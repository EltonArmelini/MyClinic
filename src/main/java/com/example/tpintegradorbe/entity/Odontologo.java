package com.example.tpintegradorbe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "odontologos")
@AllArgsConstructor @NoArgsConstructor
@Getter
@Setter
@ToString
public class Odontologo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String nombreOdontologo;
    @NotNull
    private String apellidoOdontologo;
    @Column(unique = true)
    private String matricula;

    @OneToMany(mappedBy = "odontologo")
    @JsonIgnore
    private Set<Turno> turnos = new HashSet<>();

}
