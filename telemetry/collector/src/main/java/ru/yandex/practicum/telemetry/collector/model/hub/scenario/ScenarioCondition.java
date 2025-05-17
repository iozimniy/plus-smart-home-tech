package ru.yandex.practicum.telemetry.collector.model.hub.scenario;

import ru.yandex.practicum.telemetry.collector.model.hub.constants.ConditionOperation;
import ru.yandex.practicum.telemetry.collector.model.hub.constants.ConditionType;

public class ScenarioCondition {
    String sensorId;
    ConditionType type;
    ConditionOperation operation;
    Integer value;
}
