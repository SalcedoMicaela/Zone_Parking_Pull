package ec.edu.espe.ms_clientes.model;

import ec.edu.espe.ms_clientes.utils.CedulaRucValidator;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@DiscriminatorValue("JURIDICA")
public class PersonaJuridica extends Persona{

    @Column(nullable=false)
    private String razonSocial;
    @Column(nullable=false, unique=true)
    private String representanteLegal;
    @Column
    private String actividadEconomica;

    @Override
    public boolean validarIdentificacion() {
        String id = this.getIdentificacion();

        if (id == null) return false;


        if (id.length() == 13) {
            return CedulaRucValidator.validarRuc(id);
        }

        return false;
    }




}
