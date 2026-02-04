package ec.edu.espe.ms_clientes.model;


import ec.edu.espe.ms_clientes.utils.CedulaRucValidator;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity

@DiscriminatorValue("NATURAL")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PersonaNatural extends Persona  {


    @Column
    private  String apellido;

    @Column
    private Genero genero;
    @Column (nullable = false)
    private LocalDate fechaNacimiento;

    @Override
    public boolean validarIdentificacion() {
        String id = this.getIdentificacion();

        if (id.length() == 10) {
            return CedulaRucValidator.validarCedula(id);
        }

        return false;
    }

}
