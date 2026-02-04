package ec.edu.espe.ms_clientes.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class MotoRequestDto extends VehiculoRequestDto {

    @NotNull(message = "El cilindraje es obligatorio")
    @Min(value = 50, message = "El cilindraje debe ser al menos 50cc")
    @Max(value = 2000, message = "El cilindraje no puede exceder 2000cc")
    private Integer cilindraje;

    @NotBlank(message = "El tipo de moto no puede estar vacío")
    @Pattern(
            regexp = "^(SPORT|CRUISER|TOURING|SCOOTER|DOBLE_PROPOSITO)$",
            message = "Tipo de moto inválido"
    )
    private String tipoMoto;

    @NotNull(message = "Debe indicar si incluye casco")
    private Boolean tieneCasco;

    @NotBlank(message = "La marca de la moto no puede estar vacía")
    @Pattern(
            regexp = "^(YAMAHA|HONDA|SUZUKI|KAWASAKI)$",
            message = "Marca de moto inválida"
    )
    private String marcaMoto;

    @NotBlank(message = "El tipo de frenos no puede estar vacío")
    private String tipoFrenos;

    @NotNull(message = "Debe indicar si tiene parrilla trasera")
    private Boolean tieneParrilla;
}

