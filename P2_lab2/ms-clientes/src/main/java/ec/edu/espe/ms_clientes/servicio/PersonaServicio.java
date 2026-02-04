package ec.edu.espe.ms_clientes.servicio;

import ec.edu.espe.ms_clientes.dto.request.AutomovilRequestDto;
import ec.edu.espe.ms_clientes.dto.request.PersonaJuridicaRequestDto;
import ec.edu.espe.ms_clientes.dto.request.PersonaNaturalRequestDto;
import ec.edu.espe.ms_clientes.dto.request.PersonaRequestDto;
import ec.edu.espe.ms_clientes.dto.response.MotoResponseDto;
import ec.edu.espe.ms_clientes.dto.response.PersonaResponseDto;
import ec.edu.espe.ms_clientes.dto.response.VehiculoResponseDto;
import ec.edu.espe.ms_clientes.model.PersonaJuridica;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface PersonaServicio {

    PersonaResponseDto crearPersonaNatural(PersonaNaturalRequestDto dto);
    PersonaResponseDto crearPersonaJuridica(PersonaJuridicaRequestDto dto);
    List<PersonaResponseDto> findAllPersona();

    PersonaResponseDto actualizarPersonaNatural(UUID id, PersonaNaturalRequestDto dto);
    PersonaResponseDto actualizarPersonaJuridica(UUID id, PersonaJuridicaRequestDto dto);

    void  eliminarPersona(UUID id);

    List<PersonaResponseDto> listarPersonasNaturales();
    PersonaResponseDto obtenerPersonaPorIdentificacion(String identificacion);

}
