package ec.edu.espe.ms_clientes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("MOTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@SuperBuilder
public class Moto extends Vehiculo {

    @Column(nullable=false)
    private Integer cilindraje;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private TipoMoto tipoMoto;

    @Column(nullable=false)
    private Boolean tieneCasco;

    @Enumerated(EnumType.STRING)
    private MarcaMoto marcaMoto;

    @Column(nullable=false)
    private String tipoFrenos; // Disco, ABS, Tambor

    @Column(nullable=false)
    private Boolean tieneParrilla; // Sí / No
}

