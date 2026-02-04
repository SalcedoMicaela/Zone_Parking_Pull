package ec.edu.espe.ms_clientes.servicio;

import ec.edu.espe.ms_clientes.dto.request.AutomovilRequestDto;
import ec.edu.espe.ms_clientes.dto.request.MotoRequestDto;
import ec.edu.espe.ms_clientes.dto.request.PersonaJuridicaRequestDto;
import ec.edu.espe.ms_clientes.dto.request.PersonaNaturalRequestDto;
import ec.edu.espe.ms_clientes.dto.response.MotoResponseDto;
import ec.edu.espe.ms_clientes.dto.response.PersonaResponseDto;
import ec.edu.espe.ms_clientes.dto.response.VehiculoResponseDto;

import java.util.List;
import java.util.UUID;

public interface VehiculoServicio {

    VehiculoResponseDto crearMoto(MotoRequestDto dto);

    VehiculoResponseDto crearAutomovil(AutomovilRequestDto dto);

    List<VehiculoResponseDto> findAllVehiculos();

    VehiculoResponseDto actualizarMoto(UUID id, MotoRequestDto dto);

    VehiculoResponseDto actualizarAutomovil(UUID id, AutomovilRequestDto dto);

    void eliminarVehiculo(UUID id);
    VehiculoResponseDto obtenerVehiculoPorPlaca(String placa);
    boolean validateVehiculoBelongsToPersona(UUID vehiculoId, UUID personaId);

}

