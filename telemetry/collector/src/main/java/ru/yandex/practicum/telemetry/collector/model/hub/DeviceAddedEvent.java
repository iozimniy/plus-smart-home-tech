package ru.yandex.practicum.telemetry.collector.model.hub;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import ru.yandex.practicum.telemetry.collector.model.hub.constants.DeviceType;

@Getter
public class DeviceAddedEvent extends HubEvent {
    @NotBlank
    @NotNull
    private String id;
    @NotNull
    private DeviceType deviceType;

    @Override
    public HubEventType getType() {
        return HubEventType.DEVICE_ADDED;
    }
}