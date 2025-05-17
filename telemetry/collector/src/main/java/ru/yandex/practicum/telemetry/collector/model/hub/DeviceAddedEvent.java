package ru.yandex.practicum.telemetry.collector.model.hub;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.yandex.practicum.telemetry.collector.model.hub.constants.DeviceType;

public class DeviceAddedEvent extends HubEvent {
    @NotBlank
    @NotNull
    private String id;
    @NotNull
    private DeviceType deviceType;
    @NotNull
    private HubEventType type;


    @Override
    public HubEventType getType() {
        return HubEventType.DEVICE_ADDED;
    }
}