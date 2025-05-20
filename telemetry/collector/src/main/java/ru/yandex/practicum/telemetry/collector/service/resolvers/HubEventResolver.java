package ru.yandex.practicum.telemetry.collector.service.resolvers;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEventType;
import ru.yandex.practicum.telemetry.collector.service.handler.hub.HubEventHandler;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class HubEventResolver {
    private final Map<HubEventProto.PayloadCase, HubEventHandler> hubEventHandlers;

    public HubEventResolver(List<HubEventHandler> hubEventHandlersList) {
        this.hubEventHandlers = hubEventHandlersList.stream()
                .collect(Collectors.toMap(HubEventHandler::getMessageType, Function.identity()));;
    }

    public HubEventHandler getHandler(HubEventType type) {
        if (!hubEventHandlers.containsKey(type)) {
            throw new IllegalArgumentException("Не найден обработчик для hub-события типа " + type);
        }

        return hubEventHandlers.get(type);
    }
}
