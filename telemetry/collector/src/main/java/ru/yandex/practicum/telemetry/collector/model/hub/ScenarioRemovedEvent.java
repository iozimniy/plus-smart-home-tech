package ru.yandex.practicum.telemetry.collector.model.hub;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ScenarioRemovedEvent extends HubEvent {

    @Min(value = 3)
    @Max(value = 2147483647)
    @NotNull
    private String name;

    @Override
    public HubEventType getType() {
        return HubEventType.SCENARIO_REMOVE;
    }
}
