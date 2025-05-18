package ru.yandex.practicum.telemetry.collector.model.hub;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import ru.yandex.practicum.telemetry.collector.model.hub.scenario.DeviceAction;
import ru.yandex.practicum.telemetry.collector.model.hub.scenario.ScenarioCondition;

import java.util.List;

@Getter
public class ScenarioAddedEvent extends HubEvent {
    @Max(value = 2147483647)
    @NotNull
    @Min(value = 3)
    private String name;
    private List<ScenarioCondition> conditions;
    private List<DeviceAction> actions;

    @Override
    public HubEventType getType() {
        return HubEventType.SCENARIO_ADDED;
    }
}
