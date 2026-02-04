package ec.edu.espe.ms_clientes.dto.mapper;

import ec.edu.espe.ms_clientes.dto.request.AutomovilRequestDto;
import ec.edu.espe.ms_clientes.dto.response.AutomovilResponseDto;
import ec.edu.espe.ms_clientes.model.*;
import org.springframework.stereotype.Component;

@Component
public class AutomovilMapper {


    public Automovil toEntity(AutomovilRequestDto dto, Persona propietario) {
        if (dto == null) return null;

        return Automovil.builder()
                .placa(dto.getPlaca())
                .marca(dto.getMarca())
                .modelo(dto.getModelo())
                .color(dto.getColor())
                .anioFabricacion(dto.getAnioFabricacion())
                .propietario(propietario)
                .tipoAutomovil(TipoAutomovil.valueOf(dto.getTipoAutomovil()))
                .marcaAutomovil(MarcaAutomovil.valueOf(dto.getMarcaAutomovil()))
                .cilindraje(dto.getCilindraje())
                .tipoCombustible(dto.getTipoCombustible())
                .numeroPuertas(dto.getNumeroPuertas())
                .tipoTransmision(dto.getTipoTransmision())
                .build();
    }

    public void updateEntityFromDto(AutomovilRequestDto dto, Automovil auto, Persona propietario) {

        auto.setPlaca(dto.getPlaca());
        auto.setMarca(dto.getMarca());
        auto.setModelo(dto.getModelo());
        auto.setColor(dto.getColor());
        auto.setAnioFabricacion(dto.getAnioFabricacion());
        auto.setPropietario(propietario);

        auto.setTipoAutomovil(TipoAutomovil.valueOf(dto.getTipoAutomovil()));
        auto.setMarcaAutomovil(MarcaAutomovil.valueOf(dto.getMarcaAutomovil()));
        auto.setCilindraje(dto.getCilindraje());
        auto.setTipoCombustible(dto.getTipoCombustible());
        auto.setNumeroPuertas(dto.getNumeroPuertas());
        auto.setTipoTransmision(dto.getTipoTransmision());
    }

    public AutomovilResponseDto toDto(Automovil auto) {
        if (auto == null) return null;

        return AutomovilResponseDto.builder()
                .id(auto.getId())
                .placa(auto.getPlaca())
                .marca(auto.getMarca())
                .modelo(auto.getModelo())
                .color(auto.getColor())
                .anioFabricacion(auto.getAnioFabricacion())

                .propietario(obtenerNombrePropietario(auto.getPropietario()))
                .tipo(determinarTipoVehiculo(auto))
                .tipoAutomovil(auto.getTipoAutomovil().name())
                .marcaAutomovil(auto.getMarcaAutomovil().name())
                .cilindraje(auto.getCilindraje())
                .tipoCombustible(auto.getTipoCombustible())
                .numeroPuertas(auto.getNumeroPuertas())
                .tipoTransmision(auto.getTipoTransmision())
                .activo(auto.getActivo())
                .build();
    }


    private String determinarTipoVehiculo(Vehiculo v) {
        return "AUTOMOVIL";
    }

    private String obtenerNombrePropietario(Persona persona) {

        if (persona instanceof PersonaNatural pn) {
            return pn.getNombre() + " " + pn.getApellido();
        }

        if (persona instanceof PersonaJuridica pj) {
            return pj.getRazonSocial();
        }

        return "DESCONOCIDO";
    }
}
