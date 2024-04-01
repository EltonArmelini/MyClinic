package com.example.tpintegradorbe.repository;


import com.example.tpintegradorbe.entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface IOdontologoRepository extends JpaRepository<Odontologo,Long> {

    Optional<Odontologo> findByMatricula(String matricula);
}
