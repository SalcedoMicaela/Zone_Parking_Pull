package ec.edu.espe.zone_core.zone_core.controller;

import ec.edu.espe.zone_core.zone_core.dto.SpacesRequestDto;
import ec.edu.espe.zone_core.zone_core.dto.SpacesResponseDto;
import ec.edu.espe.zone_core.zone_core.dto.ZoneResponseDto;
import ec.edu.espe.zone_core.zone_core.model.SpaceStatus;
import ec.edu.espe.zone_core.zone_core.model.Spaces;
import ec.edu.espe.zone_core.zone_core.services.SpaceService;
import ec.edu.espe.zone_core.zone_core.services.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/spaces")
public class SpaceController {

    @Autowired
    private SpaceService spaceService;

    @GetMapping("/")
    public ResponseEntity<List<SpacesResponseDto>> findAllSpaces() {
        return ResponseEntity.ok(spaceService.getSpaces());
    }

    @PostMapping("/")
    public ResponseEntity<SpacesResponseDto> createSpace(@RequestBody SpacesRequestDto spaceRequestDto) {
        return ResponseEntity.ok(spaceService.createSpace(spaceRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpacesResponseDto> updateSpace(@PathVariable UUID id, @RequestBody SpacesRequestDto spaceRequestDto) {
        return ResponseEntity.ok(spaceService.updateSpace(id, spaceRequestDto));
    }


    @GetMapping("/{id}/zone/{zoneId}")
    public ResponseEntity<SpacesResponseDto> getSpaceByIdAndZone(
            @PathVariable UUID id,
            @PathVariable UUID zoneId
    ) {
        return ResponseEntity.ok(spaceService.getSpace(id, zoneId));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpace(@PathVariable UUID id) {
        spaceService.deleteSpace(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping({"/available"})
    public ResponseEntity<List<SpacesResponseDto>> getAvailableSpaces() {
        return ResponseEntity.ok(spaceService.getAvailableSpaces());
    }

    @GetMapping("/availableByZone/{zoneName}")
    public List<SpacesResponseDto> getAvailableSpacesByZoneName(
            @PathVariable String zoneName) {
        return spaceService.getAvailableSpacesByZoneName(zoneName);
    }

    @PutMapping("/{spaceId}/status")
    public ResponseEntity<SpacesResponseDto> updateSpaceStatus(
            @PathVariable UUID spaceId,
            @RequestParam SpaceStatus status   // ✅ CORRECTO
    ) {
        return ResponseEntity.ok(
                spaceService.updateSpaceStatus(spaceId, status)
        );
    }




}
