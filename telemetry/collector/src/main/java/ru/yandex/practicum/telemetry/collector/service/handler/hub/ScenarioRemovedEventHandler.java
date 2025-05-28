package ru.yandex.practicum.telemetry.collector.service.handler.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioRemovedEventProto;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioRemovedEventAvro;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEventType;
import ru.yandex.practicum.telemetry.collector.model.hub.ScenarioRemovedEvent;
import ru.yandex.practicum.telemetry.collector.service.handler.mappers.EventMapper;
import ru.yandex.practicum.telemetry.collector.service.kafka.KafkaEventProducer;

@Component
public class ScenarioRemovedEventHandler extends BaseHubEventHandler {

    public ScenarioRemovedEventHandler(KafkaEventProducer producer, EventMapper mapper) {
        super(producer, mapper);
    }

    @Override
    public HubEventProto.PayloadCase getMessageType() {
        return HubEventProto.PayloadCase.SCENARIO_REMOVED;
    }

    @Override
    protected ScenarioRemovedEventAvro mapToAvro(HubEventProto event) {
        ScenarioRemovedEventProto realEvent = event.getScenarioRemoved();

        return ScenarioRemovedEventAvro.newBuilder()
                .setName(realEvent.getName())
                .build();
    }
}
