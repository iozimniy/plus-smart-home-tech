package ru.yandex.practicum.telemetry.collector.service.handler.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.DeviceTypeAvro;
import ru.yandex.practicum.telemetry.collector.model.hub.DeviceAddedEvent;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEventType;

@Component
public class DeviceAddedEventHandler extends BaseHubEventHandler {
    @Override
    public HubEventType getMessageType() {
        return HubEventType.DEVICE_ADDED;
    }

    @Override
    protected DeviceAddedEventAvro mapToAvro(HubEvent event) {
        DeviceAddedEvent realEvent = (DeviceAddedEvent) event;
        DeviceTypeAvro deviceTypeAvro = DeviceTypeAvro.valueOf(realEvent.getDeviceType().name());

        return DeviceAddedEventAvro.newBuilder()
                .setId(realEvent.getId())
                .setType(deviceTypeAvro)
                .build();
    }
}
