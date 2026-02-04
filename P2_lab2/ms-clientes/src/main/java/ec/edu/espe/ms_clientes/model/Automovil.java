package ec.edu.espe.ms_clientes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("Automovil")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@SuperBuilder //hacer enum tipo SUV, Crossover
public class Automovil  extends Vehiculo{

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAutomovil tipoAutomovil;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MarcaAutomovil marcaAutomovil;

    @Column(nullable = false)
    private Double cilindraje;

    @Column(nullable = false)
    private String tipoCombustible;

    @Column(nullable = false)
    private Integer numeroPuertas;

    @Column(nullable = false)
    private String tipoTransmision;


}
