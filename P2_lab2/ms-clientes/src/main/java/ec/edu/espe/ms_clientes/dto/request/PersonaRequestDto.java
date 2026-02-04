package ec.edu.espe.ms_clientes.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonaRequestDto {

    @NotBlank(message = "la identificacion no puede estar vacia")
    @Size(min = 10, max = 13, message = "la identificacion debe tener entre 10 y 13 caracteres")
    @Pattern(regexp = "\\d+", message = "la identificacion debe solo numeros" )
    private String identificacion;

    @NotBlank(message = "No puede estar vacío")
    private String nombre;

    @NotBlank(message = "El email legal no puede estar vacío")
    private String email;

    @NotBlank(message = "No puede estar vacío")
    @Pattern(regexp = "[0-9+\\-]+", message = "solo debe contener numeros" )
    private String telefono;

    @NotBlank(message = "No puede estar vacío")
    private String direccion;


}
