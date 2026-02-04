package ec.edu.espe.ms_clientes.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class VehiculoRequestDto {

    @NotBlank(message = "La placa no puede estar vacía")
    @Pattern(
            regexp = "^[A-Z]{3}-\\d{3,4}$",
            message = "La placa debe tener el formato ABC-123 o ABC-1234"
    )
    private String placa;

    @NotBlank(message = "La marca no puede estar vacía")
    private String marca;

    @NotBlank(message = "El modelo no puede estar vacío")
    private String modelo;

    @NotBlank(message = "El color no puede estar vacío")
    private String color;

    @NotNull(message = "El año de fabricación es obligatorio")
    @Min(value = 1900, message = "El año es inválido")
    @Max(value = 2100, message = "El año es inválido")
    private Integer anioFabricacion;

    @NotBlank(message = "Debe especificar el id del propietario")
    @Pattern(
            regexp = "^[0-9a-fA-F\\-]{36}$",
            message = "El idPersona debe ser un UUID válido"
    )
    private String idPersona; // Relación con Persona

}
