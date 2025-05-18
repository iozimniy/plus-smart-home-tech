package ru.yandex.practicum.telemetry.collector.model.hub;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import ru.yandex.practicum.telemetry.collector.model.hub.scenario.DeviceAction;
import ru.yandex.practicum.telemetry.collector.model.hub.scenario.ScenarioCondition;

import java.util.List;

@Getter
public class ScenarioAddedEvent extends HubEvent {
    @NotBlank
    @Size(min = 2, max = 2147483647)
    private String name;
    private List<ScenarioCondition> conditions;
    private List<DeviceAction> actions;

    @Override
    public HubEventType getType() {
        return HubEventType.SCENARIO_ADDED;
    }
}
