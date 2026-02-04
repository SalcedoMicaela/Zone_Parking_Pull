package ec.edu.espe.ms_clientes.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class PersonaJuridicaRequestDto extends PersonaRequestDto{


    @NotBlank(message = "La razón social no puede estar vacía")
    private String razonSocial;

    @NotBlank(message = "El representante legal no puede estar vacío")
    private String representanteLegal;

    @NotBlank(message = "El representante legal no puede estar vacío")
    private String actividadEconomica;

}
