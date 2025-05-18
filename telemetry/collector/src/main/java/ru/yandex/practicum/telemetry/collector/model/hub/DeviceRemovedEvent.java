package ru.yandex.practicum.telemetry.collector.model.hub;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DeviceRemovedEvent extends HubEvent {
    @NotBlank
    @NotNull
    private String id;

    @Override
    public HubEventType getType() {
        return HubEventType.DEVICE_REMOVE;
    }
}
