package ru.yandex.practicum.telemetry.collector.model.hub;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DeviceRemovedEvent extends HubEvent {
    @NotBlank
    @NotNull
    private String id;
    private HubEventType type = HubEventType.DEVICE_REMOVE;

    @Override
    public HubEventType getType() {
        return HubEventType.DEVICE_REMOVE;
    }
}
