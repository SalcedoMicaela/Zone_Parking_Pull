package ec.edu.espe.ms_clientes.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoResponseDto {

    private UUID id;

    private String placa;

    private String marca;

    private String modelo;

    private String color;

    private Integer anioFabricacion;

    private String propietario; // nombre completo

    private String tipo;

}
