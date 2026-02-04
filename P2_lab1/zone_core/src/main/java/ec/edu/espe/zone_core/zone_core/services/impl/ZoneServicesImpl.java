package ec.edu.espe.zone_core.zone_core.services.impl;

import ec.edu.espe.zone_core.zone_core.dto.ZoneRequestDto;
import ec.edu.espe.zone_core.zone_core.dto.ZoneResponseDto;
import ec.edu.espe.zone_core.zone_core.messaging.producer.NotificationProducer;
import ec.edu.espe.zone_core.zone_core.model.Zone;
import ec.edu.espe.zone_core.zone_core.repositories.SpaceRepository;
import ec.edu.espe.zone_core.zone_core.repositories.ZoneRepository;
import ec.edu.espe.zone_core.zone_core.services.ZoneService;
import ec.edu.espe.zone_core.zone_core.utils.ClientContext;
import ec.edu.espe.zone_core.zone_core.utils.ClientInfoUtil;
import ec.edu.espe.zone_core.zone_core.utils.NetworkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ZoneServicesImpl implements ZoneService {
    @Autowired
    private NotificationProducer notificationProducer;
    private final ClientInfoUtil clientInfoUtil;

    private final NetworkUtil networkUtil;
    private final ZoneRepository zoneRepository;



    public ZoneServicesImpl(
            NotificationProducer notificationProducer,
            ClientInfoUtil clientInfoUtil,
            NetworkUtil networkUtil,
            ZoneRepository zoneRepository
    ) {
        this.notificationProducer = notificationProducer;
        this.clientInfoUtil = clientInfoUtil;
        this.networkUtil = networkUtil;
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
    public ZoneResponseDto createZone(ZoneRequestDto dto) {

        Zone zone = Zone.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .capacity(dto.getCapacity())
                .type(dto.getType())
                .isActive(dto.getIsActive())
                .build();

        Zone saved = zoneRepository.save(zone);
        notificationProducer.zoneCreated(zone.getId(), zone.getName(),buildClientContext());

        return convertToResponseDto(saved);
    }

    @Override
    public ZoneResponseDto updateZone(UUID id, ZoneRequestDto dto) {
        Zone existingZone = zoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zone not found"));

        existingZone.setName(dto.getName());
        existingZone.setDescription(dto.getDescription());
        existingZone.setCapacity(dto.getCapacity());
        existingZone.setType(dto.getType());
        existingZone.setIsActive(dto.getIsActive());

        Zone zone = zoneRepository.save(existingZone);
        notificationProducer.zoneUpdated(zone.getId(), zone.getName(),buildClientContext());
        return convertToResponseDto(zone);
    }

    @Override
    public List<ZoneResponseDto> getZones() {
        List<ZoneResponseDto> zones = zoneRepository.findAll()
                .stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
        notificationProducer.zonesGet(zones.size(),buildClientContext());
        return zones;
    }

    @Override
    public void deleteZone(UUID id) {
        Zone existingZone = zoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zone not found"));
        notificationProducer.zoneDeleted(existingZone.getId(), existingZone.getName(),buildClientContext());
        zoneRepository.delete(existingZone);
    }

    @Override
    public ZoneResponseDto getZoneId(UUID id) {
        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zone not found"));
        notificationProducer.zoneGet(zone.getId(), zone.getName(),buildClientContext());
        return convertToResponseDto(zone);
    }



    private ZoneResponseDto convertToResponseDto(Zone zone) {
        return ZoneResponseDto.builder()
                .id(zone.getId())
                .name(zone.getName())
                .description(zone.getDescription())
                .capacity(zone.getCapacity())
                .type(zone.getType())
                .isActive(zone.getIsActive())
                .build();
    }
}
