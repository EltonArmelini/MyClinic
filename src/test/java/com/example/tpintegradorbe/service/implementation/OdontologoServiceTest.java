package com.example.tpintegradorbe.service.implementation;

import com.example.tpintegradorbe.dto.request.OdontologoRequestDTO;
import com.example.tpintegradorbe.dto.response.OdontologoResponseDTO;
import com.example.tpintegradorbe.exceptions.ResourceNotFoundException;
import com.example.tpintegradorbe.service.IOdontologoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class OdontologoServiceTest {
    @Autowired
    IOdontologoService iOdontologoService;


    @Test
    void crear() {
        OdontologoRequestDTO odontologoRequestDTO = new OdontologoRequestDTO();
        odontologoRequestDTO.setNombreOdontologo("Juan");
        odontologoRequestDTO.setApellidoOdontologo("Lopez");
        odontologoRequestDTO.setMatricula("ABC");


        OdontologoResponseDTO response = iOdontologoService.crear(odontologoRequestDTO);

        assertNotNull(response.getId());

    }

    @Test
    void eliminar() {
        // Arrange
        OdontologoRequestDTO odontologoRequestDTO = new OdontologoRequestDTO();
        odontologoRequestDTO.setNombreOdontologo("Juan");
        odontologoRequestDTO.setApellidoOdontologo("Lopez");
        odontologoRequestDTO.setMatricula("ABC");

        OdontologoResponseDTO response = iOdontologoService.crear(odontologoRequestDTO);

        Long id = response.getId();

        // Act
        assertDoesNotThrow(() -> iOdontologoService.eliminar(id));

        // Assert
        assertThrows(ResourceNotFoundException.class, () -> iOdontologoService.eliminar(id));

    }
}