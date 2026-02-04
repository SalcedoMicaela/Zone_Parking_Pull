package ec.edu.espe.ms_clientes.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name= "vehiculo")
@DiscriminatorColumn(name = "tipo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
public abstract class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String placa;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private Integer anioFabricacion;

    @JoinColumn(nullable = false, name = "id_persona")
    @ManyToOne(fetch = FetchType.LAZY)
    private Persona propietario;

    @Column(nullable = false)
    private Boolean activo;
    @Column(nullable = false)
    private LocalDate fechaRegistro;

    @PrePersist
    public void onCreate(){
        this.fechaRegistro = LocalDate.now();
        this.activo = true;
    }

}
