package ec.edu.espe.zone_core.zone_core.messaging.factory;

import ec.edu.espe.zone_core.zone_core.messaging.NotificationEvent;
import ec.edu.espe.zone_core.zone_core.messaging.model.ActionType;
import ec.edu.espe.zone_core.zone_core.messaging.model.EntityType;
import ec.edu.espe.zone_core.zone_core.utils.ClientContext;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NotificationEventFactory {

    private static final String MICROSERVICE = "microservice-zones";
    private static final String SEVERITY_INFO = "INFO";

    public static NotificationEvent build(
            ActionType action,
            EntityType entity,
            UUID entityId,
            String message,
            Map<String, Object> data,
            ClientContext context
    ) {

        Map<String, Object> finalData = new HashMap<>();

        if (data != null) {
            finalData.putAll(data);
        }

        if (context != null) {
            finalData.putAll(context.toMap());
        }

        return NotificationEvent.builder()
                .uuid(UUID.randomUUID())
                .microservice(MICROSERVICE)
                .entityType(entity.name())
                .entityId(entityId)
                .action(action.name())
                .message(message)
                .timestamp(LocalDateTime.now())
                .severity(SEVERITY_INFO)
                .data(finalData)
                .build();
    }
}
