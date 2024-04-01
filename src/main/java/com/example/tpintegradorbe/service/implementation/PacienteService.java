package com.example.tpintegradorbe.service.implementation;

import com.example.tpintegradorbe.dto.request.PacienteRequestDTO;
import com.example.tpintegradorbe.dto.response.PacienteResponseDTO;
import com.example.tpintegradorbe.entity.Paciente;
import com.example.tpintegradorbe.exceptions.ResourceNotFoundException;
import com.example.tpintegradorbe.exceptions.UniqueConstraintException;
import com.example.tpintegradorbe.repository.IPacienteRepository;
import com.example.tpintegradorbe.service.IPacienteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PacienteService implements IPacienteService {
    private IPacienteRepository pacienteRepository;
    private ModelMapper modelMapper;
    @Autowired
    public PacienteService(IPacienteRepository pacienteRepository,ModelMapper modelMapper) {
        this.pacienteRepository = pacienteRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PacienteResponseDTO crear(PacienteRequestDTO pacienteRequestDTO) {
        if (pacienteRepository.findByDniPaciente(pacienteRequestDTO.getDniPaciente()).isPresent()) {
            throw new UniqueConstraintException("Error al crear nuevo Paciente el "+pacienteRequestDTO.getDniPaciente()+" es existente en la BD");
        }
        Paciente paciente = modelMapper.map(pacienteRequestDTO, Paciente.class);
        return modelMapper.map(pacienteRepository.save(paciente), PacienteResponseDTO.class);
    }

    @Override
    public PacienteResponseDTO editar(Long id, PacienteRequestDTO pacienteRequestDTO) {
        Optional<Paciente> pacienteBuscado = pacienteRepository.findById(id);
        if (pacienteBuscado.isPresent()) {
            Paciente pacienteExistente = pacienteBuscado.get();
            pacienteExistente.setNombrePaciente(pacienteRequestDTO.getNombrePaciente());
            pacienteExistente.setApellidoPaciente(pacienteRequestDTO.getApellidoPaciente());
            pacienteExistente.setDomicilioPaciente(pacienteRequestDTO.getDomicilioPaciente());
            pacienteExistente.setDniPaciente(pacienteRequestDTO.getDniPaciente());
            pacienteExistente.setFechaAlta(pacienteRequestDTO.getFechaAlta());
            Paciente pacienteActualizado = pacienteRepository.save(pacienteExistente);
            return modelMapper.map(pacienteActualizado, PacienteResponseDTO.class);
        } else {
            throw new ResourceNotFoundException("El paciente con ID " + id + " no existe en la base de datos");
        }
    }

    @Override
    public void eliminar(Long id) {
        Optional<Paciente> pacienteBuscado= pacienteRepository.findById(id);
        if (pacienteBuscado.isPresent()){
            pacienteRepository.delete(pacienteBuscado.get());
        } else {
            throw new ResourceNotFoundException("El paciente con ID " + id + " no existe en la base de datos");
        }
    }
    @Override
    public PacienteResponseDTO buscarPorDni(String dni) {
        Optional<Paciente> pacienteBuscado = pacienteRepository.findByDniPaciente(dni);
        if (pacienteBuscado.isPresent()) {
            Paciente paciente = pacienteBuscado.get();
            return modelMapper.map(paciente, PacienteResponseDTO.class);
        } else {
            throw new ResourceNotFoundException("El paciente con DNI " + dni + " no existe en la base de datos");
        }
    }

    @Override
    public List<PacienteResponseDTO> listarTodos() {
        List<Paciente> pacientes = pacienteRepository.findAll();
        return pacientes.stream()
                .map(paciente -> modelMapper.map(paciente, PacienteResponseDTO.class))
                .collect(Collectors.toList());
    }
}
