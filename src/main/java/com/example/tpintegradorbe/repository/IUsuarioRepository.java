package com.example.tpintegradorbe.repository;

import com.example.tpintegradorbe.entity.Usuario;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario,Long> {

    Optional<User> findByMail(String mail);
}
