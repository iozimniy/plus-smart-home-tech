package ru.yandex.practicum.telemetry.collector.service.handler.hub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.service.handler.mappers.EventMapper;
import ru.yandex.practicum.telemetry.collector.service.kafka.KafkaEventProducer;

import static ru.yandex.practicum.telemetry.collector.service.kafka.EventTopics.HUBS_EVENTS_TOPIC;

@RequiredArgsConstructor
@Slf4j
public abstract class BaseHubEventHandler<T extends SpecificRecordBase> implements HubEventHandler {

    private final KafkaEventProducer producer;
    private final EventMapper mapper;

    @Override
    public void handle(HubEventProto event) {
        log.info("Processing HubEvent {}: hubId={}, timestamp={}", event.getPayloadCase(),
                event.getHubId(), event.getTimestamp());
        log.debug("HubEvent {}: {}", event.getPayloadCase(), event);

        T payload = mapToAvro(event);
        HubEventAvro hubEventAvro = mapper.mapHubEventToAvro(event, payload);
        producer.send(HUBS_EVENTS_TOPIC, hubEventAvro);

        log.info("Sending HubEvent {} hubId={}, timestamp={} to topic {}", event.getPayloadCase(), event.getHubId(),
                event.getTimestamp(), HUBS_EVENTS_TOPIC);
        log.debug("HubEvent {} for topic {}", event, HUBS_EVENTS_TOPIC);
    }

    protected abstract T mapToAvro(HubEventProto event);
}
