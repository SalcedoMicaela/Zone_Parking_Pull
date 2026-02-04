package ec.edu.espe.zone_core.zone_core.dto;

import ec.edu.espe.zone_core.zone_core.model.SpaceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpacesRequestDto {

    private String name;
    private String code;
    private SpaceStatus status;
    private String description;
    private Integer priority;
    private UUID idZone;


}
