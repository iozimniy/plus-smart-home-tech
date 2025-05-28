package ru.yandex.practicum.telemetry.collector.service.handler.mappers;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionProto;
import ru.yandex.practicum.kafka.telemetry.event.ActionTypeAvro;
import ru.yandex.practicum.kafka.telemetry.event.DeviceActionAvro;
import ru.yandex.practicum.telemetry.collector.model.hub.scenario.DeviceAction;

@Component
public class DeviceActionMapper {

    public static DeviceActionAvro mapToAvro(DeviceActionProto deviceAction) {

        ActionTypeAvro actionTypeAvro = ActionTypeAvro.valueOf(deviceAction.getType().name());

        return DeviceActionAvro.newBuilder()
                .setSensorId(deviceAction.getSensorId())
                .setType(actionTypeAvro)
                .setValue(deviceAction.getValue())
                .build();
    }
}
