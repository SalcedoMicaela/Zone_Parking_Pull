package ec.edu.espe.ms_clientes.dto.mapper;

import ec.edu.espe.ms_clientes.dto.request.MotoRequestDto;
import ec.edu.espe.ms_clientes.dto.response.MotoResponseDto;
import ec.edu.espe.ms_clientes.model.*;
import org.springframework.stereotype.Component;

@Component
public class MotoMapper {


    public Moto toEntity(MotoRequestDto dto, Persona propietario) {
        if (dto == null) return null;

        return Moto.builder()
                .placa(dto.getPlaca())
                .marca(dto.getMarca())
                .modelo(dto.getModelo())
                .color(dto.getColor())
                .anioFabricacion(dto.getAnioFabricacion())
                .propietario(propietario)
                .cilindraje(dto.getCilindraje())
                .tipoMoto(TipoMoto.valueOf(dto.getTipoMoto()))
                .tieneCasco(dto.getTieneCasco())
                .marcaMoto(MarcaMoto.valueOf(dto.getMarcaMoto()))
                .tipoFrenos(dto.getTipoFrenos())
                .tieneParrilla(dto.getTieneParrilla())
                .build();
    }

    public void updateEntityFromDto(MotoRequestDto dto, Moto moto, Persona propietario) {

        moto.setPlaca(dto.getPlaca());
        moto.setMarca(dto.getMarca());
        moto.setModelo(dto.getModelo());
        moto.setColor(dto.getColor());
        moto.setAnioFabricacion(dto.getAnioFabricacion());
        moto.setPropietario(propietario);

        moto.setCilindraje(dto.getCilindraje());
        moto.setTipoMoto(TipoMoto.valueOf(dto.getTipoMoto()));
        moto.setMarcaMoto(MarcaMoto.valueOf(dto.getMarcaMoto()));
        moto.setTieneCasco(dto.getTieneCasco());
        moto.setTipoFrenos(dto.getTipoFrenos());
        moto.setTieneParrilla(dto.getTieneParrilla());
    }


    public MotoResponseDto toDto(Moto moto) {
        if (moto == null) return null;

        return MotoResponseDto.builder()
                .id(moto.getId())
                .placa(moto.getPlaca())
                .marca(moto.getMarca())
                .modelo(moto.getModelo())
                .color(moto.getColor())
                .anioFabricacion(moto.getAnioFabricacion())
                .propietario(obtenerNombrePropietario(moto.getPropietario()))
                .tipo(determinarTipoVehiculo(moto))
                .cilindraje(moto.getCilindraje())
                .tipoMoto(moto.getTipoMoto().name())
                .tieneCasco(moto.getTieneCasco())
                .marcaMoto(moto.getMarcaMoto().name())
                .tipoFrenos(moto.getTipoFrenos())
                .tieneParrilla(moto.getTieneParrilla())
                .activo(moto.getActivo())
                .build();
    }



    private String determinarTipoVehiculo(Vehiculo v) {
        return "MOTO";
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

