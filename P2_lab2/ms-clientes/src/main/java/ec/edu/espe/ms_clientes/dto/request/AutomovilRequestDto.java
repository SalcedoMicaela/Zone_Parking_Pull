package ec.edu.espe.ms_clientes.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.bind.annotation.Mapping;

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
public class AutomovilRequestDto extends VehiculoRequestDto {

    @NotBlank(message = "El tipo de automóvil no puede estar vacío")
    @Pattern(
            regexp = "^(SUV|Crossover|Sedan|Convertible|Minivan|Deportivo|Familiar|Limousine)$",
            message = "Tipo de automóvil inválido"
    )
    private String tipoAutomovil;

    @NotBlank(message = "La marca del automóvil no puede estar vacía")
    @Pattern(
            regexp = "^(Kia|Chevrolet|Toyota)$",
            message = "Marca de automóvil inválida"
    )
    private String marcaAutomovil;

    @NotNull(message = "El cilindraje es obligatorio")
    @DecimalMin(value = "0.1", message = "El cilindraje debe ser mayor que 0")
    private Double cilindraje;

    @NotBlank(message = "El tipo de combustible no puede estar vacío")
    private String tipoCombustible;

    @NotNull(message = "El número de puertas es obligatorio")
    @Min(value = 2, message = "El vehículo debe tener mínimo 2 puertas")
    @Max(value = 5, message = "El vehículo no puede tener más de 5 puertas")
    private Integer numeroPuertas;

    @NotBlank(message = "El tipo de transmisión no puede estar vacío")
    private String tipoTransmision;
}
