package ec.edu.espe.ms_clientes.servicio.impl;

import ec.edu.espe.ms_clientes.dto.mapper.PersonaMapper;
import ec.edu.espe.ms_clientes.dto.request.PersonaJuridicaRequestDto;
import ec.edu.espe.ms_clientes.dto.request.PersonaNaturalRequestDto;
import ec.edu.espe.ms_clientes.dto.response.PersonaResponseDto;
import ec.edu.espe.ms_clientes.model.Persona;
import ec.edu.espe.ms_clientes.model.PersonaJuridica;
import ec.edu.espe.ms_clientes.model.PersonaNatural;
import ec.edu.espe.ms_clientes.repositorio.PersonaRepositorio;
import ec.edu.espe.ms_clientes.repositorio.VehiculoRepositorio;
import ec.edu.espe.ms_clientes.servicio.PersonaServicio;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PersonaServicioImpl implements PersonaServicio {

    private final PersonaRepositorio personaRepositorio;
    private final VehiculoRepositorio vehiculoRepositorio;
    private final PersonaMapper personaMapper;


    @Override
    public PersonaResponseDto crearPersonaNatural(PersonaNaturalRequestDto dto) {

        validarDuplicados(dto.getIdentificacion(), dto.getEmail(), dto.getTelefono());

        PersonaNatural pn = personaMapper.toEntity(dto);

        if (!pn.validarIdentificacion()) {
            throw new RuntimeException("Ingrese una identificación válida");
        }

        Persona personaGuardada = personaRepositorio.save(pn);
        log.info("Persona Natural creada con ID: {}", personaGuardada.getId());

        return personaMapper.toDto(personaGuardada);
    }

    @Override
    public PersonaResponseDto crearPersonaJuridica(PersonaJuridicaRequestDto dto) {

        validarDuplicados(dto.getIdentificacion(), dto.getEmail(), dto.getTelefono());

        PersonaJuridica pj = personaMapper.toEntity(dto);

        Persona personaGuardada = personaRepositorio.save(pj);
        log.info("Persona Jurídica creada con ID: {}", personaGuardada.getId());

        return personaMapper.toDto(personaGuardada);
    }


    @Override
    @Transactional(readOnly = true)
    public List<PersonaResponseDto> findAllPersona() {
        return personaRepositorio.findAll()
                .stream()
                .map(personaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonaResponseDto> listarPersonasNaturales() {

        return personaRepositorio.findPersonaNaturalActivas()
                .stream()
                .map(personaMapper::toDto)
                .collect(Collectors.toList());
    }
    @Override
    @Transactional(readOnly = true)
    public PersonaResponseDto obtenerPersonaPorIdentificacion(String identificacion) {

        Persona persona = personaRepositorio.findByIdentificacion(identificacion)
                .orElseThrow(() ->
                        new RuntimeException("No existe persona con identificación: " + identificacion)
                );

        return personaMapper.toDto(persona);
    }

    @Override
    public PersonaResponseDto actualizarPersonaNatural(UUID id, PersonaNaturalRequestDto dto) {

        PersonaNatural persona = (PersonaNatural) personaRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Persona Natural no encontrada"));

        personaMapper.updateEntityFromDto(dto, persona);

        if (!persona.validarIdentificacion()) {
            throw new RuntimeException("Identificación inválida");
        }

        Persona personaActualizada = personaRepositorio.save(persona);
        log.info("Persona Natural actualizada con ID: {}", personaActualizada.getId());

        return personaMapper.toDto(personaActualizada);
    }

    @Override
    public PersonaResponseDto actualizarPersonaJuridica(UUID id, PersonaJuridicaRequestDto dto) {

        PersonaJuridica persona = (PersonaJuridica) personaRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Persona Jurídica no encontrada"));

        personaMapper.updateEntityFromDto(dto, persona);

        Persona personaActualizada = personaRepositorio.save(persona);
        log.info("Persona Jurídica actualizada con ID: {}", personaActualizada.getId());

        return personaMapper.toDto(personaActualizada);
    }

    @Override
    public void eliminarPersona(UUID id) {

        Persona persona = personaRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada para eliminar"));

        personaRepositorio.delete(persona);
        log.info("Persona eliminada con ID: {}", id);
    }

    private void validarDuplicados(String identificacion, String email, String telefono) {

        if (personaRepositorio.existsByIdentificacion(identificacion)) {
            throw new RuntimeException("Ya existe una persona con esa identificación");
        }

        if (personaRepositorio.existsByEmail(email)) {
            throw new RuntimeException("Ya existe una persona con ese correo");
        }

        if (personaRepositorio.existsByTelefono(telefono)) {
            throw new RuntimeException("Ya existe una persona con ese teléfono");
        }
    }
}
