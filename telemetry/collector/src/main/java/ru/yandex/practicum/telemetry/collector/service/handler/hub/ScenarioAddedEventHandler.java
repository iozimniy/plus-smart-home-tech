package ru.yandex.practicum.telemetry.collector.service.handler.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.DeviceActionAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioConditionAvro;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEventType;
import ru.yandex.practicum.telemetry.collector.model.hub.ScenarioAddedEvent;
import ru.yandex.practicum.telemetry.collector.service.handler.mappers.DeviceActionMapper;
import ru.yandex.practicum.telemetry.collector.service.handler.mappers.EventMapper;
import ru.yandex.practicum.telemetry.collector.service.handler.mappers.ScenarioConditionMapper;
import ru.yandex.practicum.telemetry.collector.service.kafka.KafkaEventProducer;

import java.util.List;

@Component
public class ScenarioAddedEventHandler extends BaseHubEventHandler {

    public ScenarioAddedEventHandler(KafkaEventProducer producer, EventMapper mapper) {
        super(producer, mapper);
    }

    @Override
    public HubEventType getMessageType() {
        return HubEventType.SCENARIO_ADDED;
    }

    @Override
    protected ScenarioAddedEventAvro mapToAvro(HubEvent event) {
        ScenarioAddedEvent realEvent = (ScenarioAddedEvent) event;
        List<ScenarioConditionAvro> conditionList = realEvent.getConditions().stream()
                .map(ScenarioConditionMapper::mapToAvro).toList();
        List<DeviceActionAvro> deviceActionList = realEvent.getActions().stream()
                .map(DeviceActionMapper::mapToAvro).toList();

        return ScenarioAddedEventAvro.newBuilder()
                .setName(realEvent.getName())
                .setConditions(conditionList)
                .setActions(deviceActionList)
                .build();

    }
}
