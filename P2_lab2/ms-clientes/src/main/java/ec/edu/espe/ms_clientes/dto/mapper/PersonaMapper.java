package ec.edu.espe.ms_clientes.dto.mapper;

import ec.edu.espe.ms_clientes.dto.request.PersonaJuridicaRequestDto;
import ec.edu.espe.ms_clientes.dto.request.PersonaNaturalRequestDto;
import ec.edu.espe.ms_clientes.dto.response.PersonaResponseDto;
import ec.edu.espe.ms_clientes.model.Genero;
import ec.edu.espe.ms_clientes.model.Persona;
import ec.edu.espe.ms_clientes.model.PersonaJuridica;
import ec.edu.espe.ms_clientes.model.PersonaNatural;
import org.springframework.stereotype.Component;

@Component
public class PersonaMapper {

    public PersonaNatural toEntity(PersonaNaturalRequestDto dto) {
        if (dto == null) return null;

        return PersonaNatural.builder()
                .identificacion(dto.getIdentificacion())
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .genero(dto.getGenero().equalsIgnoreCase("Femenino") ? Genero.Femenino : Genero.Masculino)
                .fechaNacimiento(dto.getFechaNacimiento())
                .email(dto.getEmail())
                .telefono(dto.getTelefono())
                .direccion(dto.getDireccion())
                .build();
    }


    public PersonaJuridica toEntity(PersonaJuridicaRequestDto dto) {
        if (dto == null) return null;

        return PersonaJuridica.builder()
                .identificacion(dto.getIdentificacion())
                .razonSocial(dto.getRazonSocial())
                .representanteLegal(dto.getRepresentanteLegal())
                .actividadEconomica(dto.getActividadEconomica())
                .email(dto.getEmail())
                .telefono(dto.getTelefono())
                .direccion(dto.getDireccion())
                .nombre(dto.getNombre())
                .build();
    }


    public void updateEntityFromDto(PersonaNaturalRequestDto dto, PersonaNatural entity) {
        if (dto == null || entity == null) return;

        entity.setNombre(dto.getNombre());
        entity.setApellido(dto.getApellido());
        entity.setEmail(dto.getEmail());
        entity.setTelefono(dto.getTelefono());
        entity.setDireccion(dto.getDireccion());

        if (dto.getGenero() != null) {
            entity.setGenero(dto.getGenero().equalsIgnoreCase("Femenino") ? Genero.Femenino : Genero.Masculino);
        }

        if (dto.getFechaNacimiento() != null) {
            entity.setFechaNacimiento(dto.getFechaNacimiento());
        }
    }


    public void updateEntityFromDto(PersonaJuridicaRequestDto dto, PersonaJuridica entity) {
        if (dto == null || entity == null) return;

        entity.setRazonSocial(dto.getRazonSocial());
        entity.setRepresentanteLegal(dto.getRepresentanteLegal());
        entity.setActividadEconomica(dto.getActividadEconomica());
        entity.setEmail(dto.getEmail());
        entity.setTelefono(dto.getTelefono());
        entity.setDireccion(dto.getDireccion());
        entity.setNombre(dto.getNombre());
    }



    public PersonaResponseDto toDto(Persona persona) {
        if (persona == null) return null;

        String tipoPersona = determinarTipo(persona);
        String nombreCompleto = buildNombreCompleto(persona);

        return PersonaResponseDto.builder()
                .id(persona.getId())
                .nombre(nombreCompleto)
                .email(persona.getEmail())
                .telefono(persona.getTelefono())
                .direccion(persona.getDireccion())
                .tipo(tipoPersona)
                .activo(persona.getActivo())
                .fechaCreacion(persona.getFechaCreacion())
                .build();
    }


    private String determinarTipo(Persona persona) {
        if (persona instanceof PersonaNatural) return "NATURAL";
        if (persona instanceof PersonaJuridica) return "JURIDICA";
        return "DESCONOCIDO";
    }

    private String buildNombreCompleto(Persona persona) {
        if (persona instanceof PersonaNatural natural) {
            return natural.getNombre() + " " + natural.getApellido();
        }

        if (persona instanceof PersonaJuridica juridica) {
            return juridica.getRazonSocial();
        }

        return persona.getNombre();
    }
}
