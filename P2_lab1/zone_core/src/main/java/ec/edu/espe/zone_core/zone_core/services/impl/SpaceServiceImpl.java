package ec.edu.espe.zone_core.zone_core.services.impl;

import ec.edu.espe.zone_core.zone_core.dto.SpacesRequestDto;
import ec.edu.espe.zone_core.zone_core.dto.SpacesResponseDto;
import ec.edu.espe.zone_core.zone_core.messaging.producer.NotificationProducer;
import ec.edu.espe.zone_core.zone_core.model.SpaceStatus;
import ec.edu.espe.zone_core.zone_core.model.Spaces;
import ec.edu.espe.zone_core.zone_core.model.Zone;
import ec.edu.espe.zone_core.zone_core.repositories.SpaceRepository;
import ec.edu.espe.zone_core.zone_core.repositories.ZoneRepository;
import ec.edu.espe.zone_core.zone_core.services.SpaceService;
import ec.edu.espe.zone_core.zone_core.utils.ClientContext;
import ec.edu.espe.zone_core.zone_core.utils.ClientInfoUtil;
import ec.edu.espe.zone_core.zone_core.utils.NetworkUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SpaceServiceImpl implements SpaceService {

    @Autowired
    private NotificationProducer notificationProducer;
    private final ClientInfoUtil clientInfoUtil;
    private final NetworkUtil networkUtil;

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private ZoneRepository zoneRepository;

    public SpaceServiceImpl(
            NotificationProducer notificationProducer,
            ClientInfoUtil clientInfoUtil,
            NetworkUtil networkUtil,
            SpaceRepository spaceRepository,
            ZoneRepository zoneRepository
    ) {
        this.notificationProducer = notificationProducer;
        this.clientInfoUtil = clientInfoUtil;
        this.networkUtil = networkUtil;
        this.spaceRepository = spaceRepository;
        this.zoneRepository = zoneRepository;
    }
    private ClientContext buildClientContext() {
        return new ClientContext(
                clientInfoUtil.getClientIp(),
                clientInfoUtil.getClientHost(),
                networkUtil.getServerMac()
        );
    }


    @Override
    public SpacesResponseDto createSpace(SpacesRequestDto dto) {

        Zone zone = zoneRepository.findById(dto.getIdZone())
                .orElseThrow(() -> new RuntimeException("Zona no encontrada"));


        Spaces space = Spaces.builder()
                .name(dto.getName())
                .code(dto.getCode())
                .status(dto.getStatus())
                .description(dto.getDescription())
                .priority(dto.getPriority())
                .zone(zone)
                .build();

        Spaces saved = spaceRepository.save(space);

        notificationProducer.spaceCreated(saved.getId(), saved.getCode(), saved.getZone().getId(),buildClientContext());

        return convertToResponseDto(saved);
    }

    @Transactional
    @Override
    public SpacesResponseDto updateSpace(UUID id, SpacesRequestDto dto) {
        Spaces existing = spaceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Space not found"));

        // actualizar campos
        existing.setName(dto.getName());
        existing.setCode(dto.getCode());
        existing.setDescription(dto.getDescription());
        existing.setPriority(dto.getPriority());
        existing.setStatus(dto.getStatus());
        Zone zone = zoneRepository.findById(dto.getIdZone())
                .orElseThrow(() -> new RuntimeException("Zona no encontrada"));
        existing.setZone(zone);


        Spaces saved = spaceRepository.save(existing);
        notificationProducer.spaceUpdated(saved.getId(), saved.getCode(), saved.getZone().getId(), buildClientContext());

        return convertToResponseDto(saved);
    }

    @Override
    public SpacesResponseDto updateSpaceStatus(UUID id, SpaceStatus status){
        Spaces space= spaceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Space not found"));

        space.setStatus(status);
        Spaces updated = spaceRepository.save(space);

        return convertToResponseDto(updated);

    }

    @Override
    public SpacesResponseDto getSpace(UUID id, UUID zoneId) {

        Spaces space = spaceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Space not found"));

        if (!space.getZone().getId().equals(zoneId)) {
            throw new RuntimeException("Space does not belong to the given zone");
        }
        notificationProducer.spaceGet(space.getId(), space.getName(), zoneId, buildClientContext());

        return convertToResponseDto(space);
    }

    @Override
    public void deleteSpace(UUID spaceId) {
        Spaces space = spaceRepository.findById(spaceId)
                .orElseThrow(() -> new RuntimeException("Space not found"));
        notificationProducer.spaceDeleted(space.getId(), space.getName(), space.getZone().getId(), buildClientContext());

        spaceRepository.delete(space);
    }

    @Override
    public List<SpacesResponseDto> getSpaces() {

        List<SpacesResponseDto> spaces = spaceRepository.findAll()
                .stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
        notificationProducer.spacesGet(spaces.size(),buildClientContext());
        return spaces;
    }

    @Override
    public List<SpacesResponseDto> getAvailableSpaces() {
        return spaceRepository.findByStatus(SpaceStatus.AVAILABLE)
                .stream()
                .map(this::convertToResponseDto)
                .toList();
    }

    @Override
    public List<SpacesResponseDto> getAvailableSpacesByZoneName(String zoneName) {
        return spaceRepository
                .findByZone_NameAndStatus(zoneName, SpaceStatus.AVAILABLE)
                .stream()
                .map(this::convertToResponseDto)
                .toList();
    }









    private SpacesResponseDto convertToResponseDto(Spaces space) {
        return SpacesResponseDto.builder()
                .id(space.getId())
                .name(space.getName())
                .code(space.getCode())
                .status(space.getStatus())
                .description(space.getDescription())
                .priority(space.getPriority())
                .idZone(space.getZone().getId())

                .build();
    }
}
