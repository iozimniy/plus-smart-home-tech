package ru.yandex.practicum.telemetry.collector.model.hub;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import ru.yandex.practicum.telemetry.collector.model.hub.constants.ActionType;
import ru.yandex.practicum.telemetry.collector.model.hub.scenario.ScenarioCondition;

import java.util.List;

public class ScenarioAddedEvent extends HubEvent {
    @Max(value = 2147483647)
    @NotNull
    @Min(value = 3)
    private String name;
    @NotNull
    private List<ScenarioCondition> conditions;
    @NotNull
    private List<ActionType> action;
    @NotNull
    private HubEventType type;


    @Override
    public HubEventType getType() {
        return HubEventType.SCENARIO_ADDED;
    }
}
