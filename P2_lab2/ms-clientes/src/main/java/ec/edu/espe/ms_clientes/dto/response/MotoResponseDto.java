package ec.edu.espe.ms_clientes.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class MotoResponseDto extends VehiculoResponseDto {

    private Integer cilindraje;

    private String tipoMoto;

    private Boolean tieneCasco;

    private String marcaMoto;

    private String tipoFrenos;

    private Boolean tieneParrilla;

    private Boolean activo;
}
