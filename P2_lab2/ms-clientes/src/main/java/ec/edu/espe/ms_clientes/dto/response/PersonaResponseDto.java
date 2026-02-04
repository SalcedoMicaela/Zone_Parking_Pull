package ec.edu.espe.ms_clientes.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PersonaResponseDto {

    private UUID id;
    private String nombre; //mandar nombre  completo
    private String email;
    private String telefono;
    private String direccion;
    private String tipo; //natural o juridica
    private Boolean activo;
    private LocalDateTime fechaCreacion;

}
