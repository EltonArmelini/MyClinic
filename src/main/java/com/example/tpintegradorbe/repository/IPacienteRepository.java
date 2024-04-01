package com.example.tpintegradorbe.repository;


import com.example.tpintegradorbe.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPacienteRepository extends JpaRepository<Paciente,Long> {

    Optional<Paciente> findByDniPaciente(String dni);
}
