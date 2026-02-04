package ec.edu.espe.ms_clientes.servicio.impl;

import ec.edu.espe.ms_clientes.dto.mapper.AutomovilMapper;
import ec.edu.espe.ms_clientes.dto.mapper.MotoMapper;
import ec.edu.espe.ms_clientes.dto.request.AutomovilRequestDto;
import ec.edu.espe.ms_clientes.dto.request.MotoRequestDto;
import ec.edu.espe.ms_clientes.dto.response.VehiculoResponseDto;
import ec.edu.espe.ms_clientes.model.*;
import ec.edu.espe.ms_clientes.repositorio.PersonaRepositorio;
import ec.edu.espe.ms_clientes.repositorio.VehiculoRepositorio;
import ec.edu.espe.ms_clientes.servicio.VehiculoServicio;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class VehiculoServicioImpl implements VehiculoServicio {

    private final VehiculoRepositorio vehiculoRepositorio;
    private final PersonaRepositorio personaRepositorio;
    private final MotoMapper motoMapper;
    private final AutomovilMapper automovilMapper;



    @Override
    public VehiculoResponseDto crearMoto(MotoRequestDto dto) {

        Persona propietario = personaRepositorio.findById(UUID.fromString(dto.getIdPersona()))
                .orElseThrow(() -> new RuntimeException("Propietario no encontrado"));

        Moto moto = motoMapper.toEntity(dto, propietario);

        Moto guardado = vehiculoRepositorio.save(moto);

        log.info("Moto creada con ID {}", guardado.getId());

        return motoMapper.toDto(guardado);
    }




    @Override
    public VehiculoResponseDto crearAutomovil(AutomovilRequestDto dto) {

        Persona propietario = personaRepositorio.findById(UUID.fromString(dto.getIdPersona()))
                .orElseThrow(() -> new RuntimeException("Propietario no encontrado"));

        Automovil auto = automovilMapper.toEntity(dto, propietario);

        Automovil guardado = vehiculoRepositorio.save(auto);
        log.info("Automóvil creado con ID {}", guardado.getId());

        return automovilMapper.toDto(guardado);
    }




    @Override
    public List<VehiculoResponseDto> findAllVehiculos() {

        return vehiculoRepositorio.findAllWithPropietario()
                .stream()
                .map(v -> {
                    if (v instanceof Moto moto) return motoMapper.toDto(moto);
                    if (v instanceof Automovil auto) return automovilMapper.toDto(auto);
                    throw new IllegalStateException("Tipo de vehículo no soportado");
                })
                .toList();
    }




    @Override
    public VehiculoResponseDto actualizarMoto(UUID id, MotoRequestDto dto) {

        Moto moto = (Moto) vehiculoRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Moto no encontrada"));

        Persona propietario = personaRepositorio.findById(UUID.fromString(dto.getIdPersona()))
                .orElseThrow(() -> new RuntimeException("Propietario no encontrado"));

        motoMapper.updateEntityFromDto(dto, moto, propietario);

        Moto actualizado = vehiculoRepositorio.save(moto);

        log.info("Moto actualizada con ID {}", actualizado.getId());

        return motoMapper.toDto(actualizado);
    }


    @Override
    public VehiculoResponseDto actualizarAutomovil(UUID id, AutomovilRequestDto dto) {

        Automovil auto = (Automovil) vehiculoRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Automóvil no encontrado"));

        Persona propietario = personaRepositorio.findById(UUID.fromString(dto.getIdPersona()))
                .orElseThrow(() -> new RuntimeException("Propietario no encontrado"));

        automovilMapper.updateEntityFromDto(dto, auto, propietario);

        Automovil actualizado = vehiculoRepositorio.save(auto);

        log.info("Automóvil actualizado con ID {}", actualizado.getId());

        return automovilMapper.toDto(actualizado);
    }



    @Override
    public void eliminarVehiculo(UUID id) {

        Vehiculo vehiculo = vehiculoRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        vehiculoRepositorio.delete(vehiculo);

        log.info("Vehículo eliminado con ID {}", id);
    }
    @Override
    public boolean validateVehiculoBelongsToPersona(UUID vehiculoId, UUID personaId) {
        if (!vehiculoRepositorio.existsById(vehiculoId)) {
            throw new EntityNotFoundException("Vehículo no encontrado");
        }
        return vehiculoRepositorio.existsByIdAndPropietario_Id(vehiculoId, personaId);
    }

    @Override
    public VehiculoResponseDto obtenerVehiculoPorPlaca(String placa) {

        Vehiculo vehiculo = vehiculoRepositorio.findByPlacaWithPropietario(placa)
                .orElseThrow(() ->
                        new RuntimeException("No existe vehículo con placa: " + placa)
                );

        if (vehiculo instanceof Moto moto) {
            return motoMapper.toDto(moto);
        }

        if (vehiculo instanceof Automovil auto) {
            return automovilMapper.toDto(auto);
        }

        throw new RuntimeException("Tipo de vehículo no soportado");
    }

}
