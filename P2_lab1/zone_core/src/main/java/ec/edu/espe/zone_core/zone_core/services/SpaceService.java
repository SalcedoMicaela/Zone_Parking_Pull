package ec.edu.espe.zone_core.zone_core.services;

import ec.edu.espe.zone_core.zone_core.dto.SpacesResponseDto;
import ec.edu.espe.zone_core.zone_core.dto.ZoneRequestDto;
import ec.edu.espe.zone_core.zone_core.model.SpaceStatus;
import org.springframework.stereotype.Service;
import ec.edu.espe.zone_core.zone_core.dto.SpacesRequestDto;
import ec.edu.espe.zone_core.zone_core.model.Spaces;


import java.util.List;
import java.util.UUID;

@Service
public interface SpaceService {

    SpacesResponseDto createSpace(SpacesRequestDto dto);
    SpacesResponseDto updateSpace(UUID id, SpacesRequestDto dto);
    SpacesResponseDto getSpace(UUID id, UUID zoneId);
    void deleteSpace(UUID spaceId);
    List<SpacesResponseDto> getSpaces();
    List<SpacesResponseDto> getAvailableSpaces();
    List<SpacesResponseDto> getAvailableSpacesByZoneName(String zoneName);
    SpacesResponseDto updateSpaceStatus(UUID id, SpaceStatus status);


}
