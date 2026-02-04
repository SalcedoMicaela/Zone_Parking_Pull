package ec.edu.espe.ms_clientes.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

@EqualsAndHashCode(callSuper = true)
@Data
public class AutomovilResponseDto extends VehiculoResponseDto {

    private String tipoAutomovil;

    private String marcaAutomovil;

    private Double cilindraje;

    private String tipoCombustible;

    private Integer numeroPuertas;

    private String tipoTransmision;
    private Boolean activo;
}
