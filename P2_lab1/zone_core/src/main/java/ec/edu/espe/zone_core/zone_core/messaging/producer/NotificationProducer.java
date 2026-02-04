package ec.edu.espe.zone_core.zone_core.messaging.producer;

import ec.edu.espe.zone_core.zone_core.messaging.NotificationEvent;
import ec.edu.espe.zone_core.zone_core.messaging.factory.NotificationEventFactory;
import ec.edu.espe.zone_core.zone_core.messaging.model.ActionType;
import ec.edu.espe.zone_core.zone_core.messaging.model.EntityType;
import ec.edu.espe.zone_core.zone_core.utils.ClientContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationProducer {

    private final RabbitTemplate rabbitTemplate;

    private static final String EXCHANGE = "Notificaciones.Exchange";
    private static final String ROUTING_KEY = "notificaciones";
    private static final UUID GLOBAL_ENTITY_ID =
            UUID.fromString("00000000-0000-0000-0000-000000000000");




    private void send(NotificationEvent event) {
        //publicar el evento rabbit
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, event);
        log.info(
                "Evento enviado | action={} | entity={} | entityId={}",
                event.getAction(),
                event.getEntityType(),
                event.getEntityId()
        );
    }



    public void spaceCreated(UUID id, String name, UUID zoneId, ClientContext context) {
        send(spaceEvent(ActionType.CREATE, id, name, zoneId, context));
    }

    public void spaceUpdated(UUID id, String name, UUID zoneId, ClientContext context) {
        send(spaceEvent(ActionType.UPDATE, id, name, zoneId, context));
    }

    public void spaceDeleted(UUID id, String name, UUID zoneId, ClientContext context) {
        send(spaceEvent(ActionType.DELETE, id, name, zoneId, context));
    }

    public void spaceGet(UUID id, String name, UUID zoneId, ClientContext context) {
        send(spaceEvent(ActionType.GET, id, name, zoneId, context));
    }

    public void spacesGet(int total, ClientContext context) {
        Map<String, Object> data = new HashMap<>();
        data.put("totalSpaces", total);

        send(NotificationEventFactory.build(
                ActionType.GET_ALL,
                EntityType.SPACE,
                GLOBAL_ENTITY_ID,
                "Consulta de lista de espacios",
                data,
                context
        ));
    }

    // ================= ZONE EVENTS =================

    public void zoneCreated(UUID id, String name, ClientContext context) {
        send(zoneEvent(ActionType.CREATE, id, name, context));
    }

    public void zoneUpdated(UUID id, String name, ClientContext context) {
        send(zoneEvent(ActionType.UPDATE, id, name, context));
    }

    public void zoneDeleted(UUID id, String name, ClientContext context) {
        send(zoneEvent(ActionType.DELETE, id, name, context));
    }

    public void zoneGet(UUID id, String name, ClientContext context) {
        send(zoneEvent(ActionType.GET, id, name, context));
    }

    public void zonesGet(int total, ClientContext context) {
        Map<String, Object> data = new HashMap<>();
        data.put("totalZones", total);

        send(NotificationEventFactory.build(
                ActionType.GET_ALL,
                EntityType.ZONE,
                GLOBAL_ENTITY_ID,
                "Consulta de lista de zonas",
                data,
                context
        ));
    }

    // ================= HELPERS =================

    private NotificationEvent spaceEvent(
            ActionType action,
            UUID id,
            String name,
            UUID zoneId,
            ClientContext context
    ) {
        Map<String, Object> data = new HashMap<>();
        data.put("spaceName", name);
        data.put("zoneId", zoneId.toString());

        return NotificationEventFactory.build(
                action,
                EntityType.SPACE,
                id,
                message(action, "Espacio", name),
                data,
                context
        );
    }

    private NotificationEvent zoneEvent(
            ActionType action,
            UUID id,
            String name,
            ClientContext context
    ) {
        Map<String, Object> data = new HashMap<>();
        data.put("zoneName", name);

        return NotificationEventFactory.build(
                action,
                EntityType.ZONE,
                id,
                message(action, "Zona", name),
                data,
                context
        );
    }

    private String message(ActionType action, String entity, String name) {
        return switch (action) {
            case CREATE -> entity + " creada: " + name;
            case UPDATE -> entity + " actualizada: " + name;
            case DELETE -> entity + " eliminada: " + name;
            case GET -> "Consulta de " + entity.toLowerCase() + ": " + name;
            case GET_ALL -> "Consulta de lista de " + entity.toLowerCase() + "s";
        };
    }
}
