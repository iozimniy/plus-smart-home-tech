package ru.yandex.practicum.telemetry.collector.service.resolvers;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEventType;
import ru.yandex.practicum.telemetry.collector.service.handler.sensor.SensorEventHandler;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SensorEventResolver {
    private final Map<SensorEventProto.PayloadCase, SensorEventHandler> sensorEventHandlers;

    public SensorEventResolver(List<SensorEventHandler> sensorEventHandlersList) {
        this.sensorEventHandlers = sensorEventHandlersList.stream()
                .collect(Collectors.toMap(SensorEventHandler::getMessageType, Function.identity()));
    }

    public SensorEventHandler getHandler(SensorEventProto.PayloadCase type) {
        if (!sensorEventHandlers.containsKey(type)) {
            throw new IllegalArgumentException("Не найден обработчик для hub-события типа " + type);
        }

        return sensorEventHandlers.get(type);
    }
}
