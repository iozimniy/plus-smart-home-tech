package ru.yandex.practicum.telemetry.collector.service.handler.mappers;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.ConditionOperationAvro;
import ru.yandex.practicum.kafka.telemetry.event.ConditionTypeAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioConditionAvro;
import ru.yandex.practicum.telemetry.collector.model.hub.scenario.ScenarioCondition;

@Component
public class ScenarioConditionMapper {

    public static ScenarioConditionAvro mapToAvro(ScenarioCondition scenarioCondition) {

        ConditionTypeAvro conditionTypeAvro = ConditionTypeAvro.valueOf(scenarioCondition.getType().name());
        ConditionOperationAvro conditionOperationAvro =
                ConditionOperationAvro.valueOf(scenarioCondition.getOperation().name());

        return ScenarioConditionAvro.newBuilder()
                .setSensorId(scenarioCondition.getSensorId())
                .setType(conditionTypeAvro)
                .setOperation(conditionOperationAvro)
                .setValue(scenarioCondition.getValue())
                .build();
    }
}
