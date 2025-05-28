package ru.yandex.practicum.telemetry.collector.service.handler.mappers;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioConditionProto;
import ru.yandex.practicum.kafka.telemetry.event.ConditionOperationAvro;
import ru.yandex.practicum.kafka.telemetry.event.ConditionTypeAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioConditionAvro;
import ru.yandex.practicum.telemetry.collector.model.hub.scenario.ScenarioCondition;

@Component
public class ScenarioConditionMapper {

    public static ScenarioConditionAvro mapToAvro(ScenarioConditionProto scenarioCondition) {

        ConditionTypeAvro conditionTypeAvro = ConditionTypeAvro.valueOf(scenarioCondition.getType().name());
        ConditionOperationAvro conditionOperationAvro =
                ConditionOperationAvro.valueOf(scenarioCondition.getOperation().name());

        ScenarioConditionAvro.Builder avroBuilder = ScenarioConditionAvro.newBuilder();
        avroBuilder.setSensorId(scenarioCondition.getSensorId());
        avroBuilder.setType(conditionTypeAvro);
        avroBuilder.setOperation(conditionOperationAvro);

        if (scenarioCondition.hasIntValue()) {
            avroBuilder.setValue(scenarioCondition.getIntValue());
        } else if (scenarioCondition.hasBoolValue()) {
            avroBuilder.setValue(scenarioCondition.getBoolValue());
        } else {
            avroBuilder.setValue(null);
        }

        return avroBuilder.build();
    }
}
