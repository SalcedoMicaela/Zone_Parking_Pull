package ec.edu.espe.zone_core.zone_core.repositories;

import ec.edu.espe.zone_core.zone_core.model.SpaceStatus;
import ec.edu.espe.zone_core.zone_core.model.Spaces;
import ec.edu.espe.zone_core.zone_core.model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface SpaceRepository extends JpaRepository<Spaces, UUID> {

    List<Spaces> findByZoneId(UUID zoneId);
    List<Spaces> findByStatus(SpaceStatus status);
    List<Spaces> findByZone_NameAndStatus(String zoneName, SpaceStatus status);

}

