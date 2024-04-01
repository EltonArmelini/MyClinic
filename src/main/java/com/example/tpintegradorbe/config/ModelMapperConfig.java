package com.example.tpintegradorbe.config;


import com.example.tpintegradorbe.dto.request.TurnoRequestDTO;
import com.example.tpintegradorbe.dto.response.TurnoResponseDTO;
import com.example.tpintegradorbe.entity.Turno;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.typeMap(TurnoRequestDTO.class, Turno.class).addMappings(mapper -> {
            mapper.map(src -> src.getDniPaciente(), Turno::setPaciente);
            mapper.map(src -> src.getMatricula(), Turno::setOdontologo);
        });

        modelMapper.typeMap(Turno.class, TurnoResponseDTO.class).addMappings(mapper -> {
            mapper.map(src -> src.getPaciente().getNombrePaciente(), TurnoResponseDTO::setNombrePaciente);
            mapper.map(src -> src.getPaciente().getApellidoPaciente(), TurnoResponseDTO::setApellidoPaciente);
            mapper.map(src -> src.getOdontologo().getNombreOdontologo(), TurnoResponseDTO::setNombreOdontologo);
            mapper.map(src -> src.getOdontologo().getApellidoOdontologo(), TurnoResponseDTO::setApellidoOdontologo);
        });

        return modelMapper;
    }
}
