package ru.yandex.practicum.telemetry.collector.service.handler.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioAddedEventProto;
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
    public HubEventProto.PayloadCase getMessageType() {
        return HubEventProto.PayloadCase.SCENARIO_ADDED;
    }

    @Override
    protected ScenarioAddedEventAvro mapToAvro(HubEventProto event) {
        ScenarioAddedEventProto realEvent = event.getScenarioAdded();

        List<ScenarioConditionAvro> conditionList = realEvent.getConditionList().stream()
                .map(ScenarioConditionMapper::mapToAvro).toList();
        List<DeviceActionAvro> deviceActionList = realEvent.getActionList().stream()
                .map(DeviceActionMapper::mapToAvro).toList();

        return ScenarioAddedEventAvro.newBuilder()
                .setName(realEvent.getName())
                .setConditions(conditionList)
                .setActions(deviceActionList)
                .build();

    }
}
