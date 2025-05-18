package ru.yandex.practicum.telemetry.collector.model.hub;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import ru.yandex.practicum.telemetry.collector.model.hub.constants.ActionType;
import ru.yandex.practicum.telemetry.collector.model.hub.scenario.DeviceAction;
import ru.yandex.practicum.telemetry.collector.model.hub.scenario.ScenarioCondition;

import java.util.List;

@Getter
public class ScenarioAddedEvent extends HubEvent {
    @Max(value = 2147483647)
    @NotNull
    @Min(value = 3)
    private String name;
    @NotNull
    private List<ScenarioCondition> conditions;
    @NotNull
    private List<DeviceAction> actions;
    @NotNull
    private HubEventType type;


    @Override
    public HubEventType getType() {
        return HubEventType.SCENARIO_ADDED;
    }
}
