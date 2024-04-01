package com.example.tpintegradorbe.service.implementation;

import com.example.tpintegradorbe.dto.request.TurnoEditRequestDTO;
import com.example.tpintegradorbe.dto.request.TurnoRequestDTO;
import com.example.tpintegradorbe.dto.response.TurnoResponseDTO;
import com.example.tpintegradorbe.dto.response.TurnoSerchByOdontologoResponseDTO;
import com.example.tpintegradorbe.entity.Odontologo;
import com.example.tpintegradorbe.entity.Paciente;
import com.example.tpintegradorbe.entity.Turno;
import com.example.tpintegradorbe.exceptions.ResourceNotFoundException;
import com.example.tpintegradorbe.repository.IOdontologoRepository;
import com.example.tpintegradorbe.repository.IPacienteRepository;
import com.example.tpintegradorbe.repository.ITurnoRepository;
import com.example.tpintegradorbe.service.ITurnoService;
import jakarta.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class TurnoService implements ITurnoService {
    private ITurnoRepository turnoRepository;
    private ModelMapper modelMapper;
    private IPacienteRepository pacienteRepository;
    private IOdontologoRepository odontologoRepository;

    @Autowired
    public TurnoService(ITurnoRepository turnoRepository, ModelMapper modelMapper, IPacienteRepository pacienteRepository, IOdontologoRepository odontologoRepository) {
        this.turnoRepository = turnoRepository;
        this.modelMapper = modelMapper;
        this.pacienteRepository = pacienteRepository;
        this.odontologoRepository = odontologoRepository;
    }

    @Override
    public TurnoResponseDTO crearTurno(TurnoRequestDTO turnoRequestDTO) {
        Paciente paciente = pacienteRepository.findByDniPaciente(turnoRequestDTO.getDniPaciente())
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con DNI: " + turnoRequestDTO.getDniPaciente()));

        Odontologo odontologo = odontologoRepository.findByMatricula(turnoRequestDTO.getMatricula())
                .orElseThrow(() -> new ResourceNotFoundException("Odontólogo no encontrado con matrícula: " + turnoRequestDTO.getMatricula()));

        Turno turno = modelMapper.map(turnoRequestDTO, Turno.class);
        turno.setPaciente(paciente);
        turno.setOdontologo(odontologo);

        Turno nuevoTurno = turnoRepository.save(turno);

        return modelMapper.map(nuevoTurno, TurnoResponseDTO.class);
    }

    @Override
    public TurnoResponseDTO editarTurno(TurnoEditRequestDTO turnoEditRequestDTO) {
        Optional<Turno> optionalTurno = turnoRepository.findById(turnoEditRequestDTO.getIdTurno());
        if (optionalTurno.isPresent()) {
            Turno turno = optionalTurno.get();
            turno.setFechaTurno(turnoEditRequestDTO.getFechaTurno());

            Optional<Paciente> optionalPaciente = pacienteRepository.findByDniPaciente(turnoEditRequestDTO.getDniPaciente());
            optionalPaciente.ifPresent(turno::setPaciente);

            Optional<Odontologo> optionalOdontologo = odontologoRepository.findByMatricula(turnoEditRequestDTO.getMatricula());
            optionalOdontologo.ifPresent(turno::setOdontologo);

            Turno turnoActualizado = turnoRepository.save(turno);

            return modelMapper.map(turnoActualizado, TurnoResponseDTO.class);
        } else {
            throw new ResourceNotFoundException("El turno con ID " + turnoEditRequestDTO.getIdTurno() + " no existe en la base de datos");
        }
    }

    @Override
    public void eliminarTurno(Long id) {
        Optional<Turno> turnoBuscado = turnoRepository.findById(id);
        if (turnoBuscado.isPresent()) {
            turnoRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("El turno con ID " + id + " no existe en la base de datos");
        }
    }
    @Override
    public TurnoResponseDTO buscarTurnoPorId(Long id) {
        Optional<Turno> turnoBuscado = turnoRepository.findById(id);
        Turno turno = turnoBuscado.orElseThrow(() -> new ResourceNotFoundException("El turno con ID " + id + " no existe en la base de datos"));
        return modelMapper.map(turno, TurnoResponseDTO.class);
    }

    @Override
    public List<TurnoResponseDTO> listarTodosLosTurnos() {
        List<Turno> turnos = turnoRepository.findAll();
        return turnos.stream()
                .map(turno -> modelMapper.map(turno, TurnoResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TurnoSerchByOdontologoResponseDTO> buscarTurnosPorOdontologo(String matricula) {
        List<Turno> turnos = turnoRepository.findByOdontologoMatricula(matricula);
        if (turnos.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron turnos.html para el odontólogo con matrícula: " + matricula);
        }

        return turnos.stream()
                .map(turno -> {
                    TurnoSerchByOdontologoResponseDTO dto = new TurnoSerchByOdontologoResponseDTO();
                    dto.setIdTurno(turno.getIdTurno());
                    dto.setFecha(turno.getFechaTurno().toString());
                    dto.setDniPaciente(turno.getPaciente().getDniPaciente());
                    return dto;
                })
                .collect(Collectors.toList());
    }
    @Override
    public List<TurnoResponseDTO> buscarTurnosPorFecha(LocalDate fecha) {
        List<Turno> turnos = turnoRepository.findByFechaTurno(fecha);
        if (turnos.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron turnos.html para la fecha: " + fecha);
        }

        return turnos.stream()
                .map(turno -> modelMapper.map(turno, TurnoResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TurnoResponseDTO> buscarTurnosPorDniPaciente(String dniPaciente) {
        List<Turno> turnos = turnoRepository.findByPaciente_DniPaciente(dniPaciente);
        if (turnos.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron turnos.html para el paciente con DNI: " + dniPaciente);
        }

        return turnos.stream()
                .map(turno -> {
                    TurnoResponseDTO dto = new TurnoResponseDTO();
                    dto.setIdTurno(turno.getIdTurno());
                    dto.setFechaTurno(turno.getFechaTurno());
                    dto.setNombrePaciente(turno.getPaciente().getNombrePaciente());
                    dto.setApellidoPaciente(turno.getPaciente().getApellidoPaciente());
                    dto.setNombreOdontologo(turno.getOdontologo().getNombreOdontologo());
                    dto.setApellidoOdontologo(turno.getOdontologo().getApellidoOdontologo());
                    return dto;
                })
                .collect(Collectors.toList());
    }

}
