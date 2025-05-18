package ru.yandex.practicum.telemetry.collector.service.handler.hub;

import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.service.handler.mappers.EventMapper;
import ru.yandex.practicum.telemetry.collector.service.kafka.KafkaEventProducer;

import static ru.yandex.practicum.telemetry.collector.service.kafka.EventTopics.HUBS_EVENTS_TOPIC;

@RequiredArgsConstructor
public abstract class BaseHubEventHandler<T extends SpecificRecordBase> implements HubEventHandler{

    private final KafkaEventProducer producer;
    private EventMapper mapper;

    @Override
    public void handle(HubEvent event) {
        T payload = mapToAvro(event);
        HubEventAvro hubEventAvro = mapper.mapHubEventToAvro(event, payload);
        producer.send(HUBS_EVENTS_TOPIC, hubEventAvro);
    }

    protected abstract T mapToAvro(HubEvent event);
}
