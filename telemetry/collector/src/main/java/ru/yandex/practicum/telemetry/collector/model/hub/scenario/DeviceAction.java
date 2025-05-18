package ru.yandex.practicum.telemetry.collector.model.hub.scenario;

import lombok.Getter;
import ru.yandex.practicum.telemetry.collector.model.hub.constants.ActionType;

@Getter
public class DeviceAction {
    String sensorId;
    ActionType type;
    Integer value;
}
