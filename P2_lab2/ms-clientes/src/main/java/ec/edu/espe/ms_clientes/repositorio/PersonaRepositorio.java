package ec.edu.espe.ms_clientes.repositorio;

import ec.edu.espe.ms_clientes.model.Persona;
import ec.edu.espe.ms_clientes.model.PersonaNatural;
import ec.edu.espe.ms_clientes.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonaRepositorio extends JpaRepository<Persona, UUID> {

   Optional<Persona> findByIdentificacion(String identificacion);
   boolean existsByIdentificacion(String identificacion);
   boolean existsByEmail(String email);
   boolean existsByTelefono(String telefono);
    @Query("SELECT pn FROM PersonaNatural pn WHERE pn.activo = true")
    List<PersonaNatural> findPersonaNaturalActivas();

   @Query("SELECT p FROM Persona p Where LOWER(p.nombre) LIKE LOWER(concat('%',:nombre, '%')) AND p.activo=true")
   List<Persona> findByNombreContainingIgnore(@Param("nombre") String nombre);




}
