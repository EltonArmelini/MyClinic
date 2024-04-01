package com.example.tpintegradorbe.service.implementation;



import com.example.tpintegradorbe.dto.request.OdontologoRequestDTO;
import com.example.tpintegradorbe.dto.response.OdontologoResponseDTO;
import com.example.tpintegradorbe.entity.Odontologo;
import com.example.tpintegradorbe.exceptions.ResourceNotFoundException;
import com.example.tpintegradorbe.exceptions.UniqueConstraintException;
import com.example.tpintegradorbe.repository.IOdontologoRepository;
import com.example.tpintegradorbe.service.IOdontologoService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OdontologoService implements IOdontologoService {

    private IOdontologoRepository odontologoRepository;
    private ModelMapper modelMapper;

    @Autowired
    public OdontologoService(IOdontologoRepository odontologoRepository, ModelMapper modelMapper){
        this.odontologoRepository=odontologoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public OdontologoResponseDTO crear(OdontologoRequestDTO odontologoRequestDTO) {
        if (odontologoRepository.findByMatricula(odontologoRequestDTO.getMatricula()).isPresent()) {
            throw new UniqueConstraintException("Error al crear nuevo Odontólogo, la matrícula "+odontologoRequestDTO.getMatricula()+" ya existe en la base de datos");
        }
        Odontologo odontologo = modelMapper.map(odontologoRequestDTO, Odontologo.class);
        return modelMapper.map(odontologoRepository.save(odontologo), OdontologoResponseDTO.class);
    }

    @Override
    public OdontologoResponseDTO editar(Long id, OdontologoRequestDTO odontologoRequestDTO) {
        Optional<Odontologo> odontologoBuscado = odontologoRepository.findByMatricula(odontologoRequestDTO.getMatricula());
        if (odontologoBuscado.isPresent()) {
            Odontologo odontologoExistente = odontologoBuscado.get();
            odontologoExistente.setNombreOdontologo(odontologoRequestDTO.getNombreOdontologo());
            odontologoExistente.setApellidoOdontologo(odontologoRequestDTO.getApellidoOdontologo());
            odontologoExistente.setMatricula(odontologoRequestDTO.getMatricula());
            Odontologo odontologoActualizado = odontologoRepository.save(odontologoExistente);
            return modelMapper.map(odontologoActualizado, OdontologoResponseDTO.class);
        } else {
            throw new ResourceNotFoundException("El odontólogo con Matricula " + odontologoRequestDTO.getMatricula() + " ya existe en la base de datos");
        }
    }

    @Override
    public void eliminar(Long id) {
        Optional<Odontologo> odontologoBuscado = odontologoRepository.findById(id);
        if (odontologoBuscado.isPresent()) {
            odontologoRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("El odontólogo con ID " + id + " no existe en la base de datos");
        }
    }

    @Override
    public OdontologoResponseDTO buscarPorMatricula(String matricula) {
        Optional<Odontologo> odontologoBuscado = odontologoRepository.findByMatricula(matricula);
        if (odontologoBuscado.isPresent()) {
            Odontologo odontologo = odontologoBuscado.get();
            return modelMapper.map(odontologo, OdontologoResponseDTO.class);
        } else {
            throw new ResourceNotFoundException("El odontólogo con matrícula " + matricula + " no existe en la base de datos");
        }
    }

    @Override
    public List<OdontologoResponseDTO> listarTodos() {
        List<Odontologo> odontologos = odontologoRepository.findAll();
        return odontologos.stream()
                .map(odontologo -> modelMapper.map(odontologo, OdontologoResponseDTO.class))
                .collect(Collectors.toList());
    }
}
