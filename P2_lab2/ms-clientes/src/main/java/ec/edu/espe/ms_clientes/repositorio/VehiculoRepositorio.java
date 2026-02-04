package ec.edu.espe.ms_clientes.repositorio;

import ec.edu.espe.ms_clientes.model.Persona;
import ec.edu.espe.ms_clientes.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VehiculoRepositorio extends JpaRepository<Vehiculo, UUID> {

    Optional<Vehiculo> findByPlaca(String placa);
    boolean existsByPlaca(String Placa);
    boolean existsByIdAndPropietario_Id(UUID id, UUID propietarioId);

    @Query(" SELECT v FROM Vehiculo v JOIN FETCH v.propietario" )
    List<Vehiculo> findAllWithPropietario();

    @Query("""
    SELECT v
    FROM Vehiculo v
    JOIN FETCH v.propietario
    WHERE v.placa = :placa
""")
    Optional<Vehiculo> findByPlacaWithPropietario(@Param("placa") String placa);



}
