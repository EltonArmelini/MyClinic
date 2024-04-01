package com.example.tpintegradorbe.repository;



import com.example.tpintegradorbe.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ITurnoRepository extends JpaRepository<Turno,Long> {

    List<Turno> findByPaciente_DniPaciente(String dni);
    List<Turno> findByFechaTurno(LocalDate fecha);
    List<Turno> findByOdontologoMatricula(String matricula);

}
