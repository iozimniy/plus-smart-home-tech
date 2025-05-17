package ru.yandex.practicum.telemetry.collector.service.handler.hub;

import ru.yandex.practicum.telemetry.collector.model.hub.HubEvent;

public abstract class BaseHubEventHandler implements HubEventHandler{

    @Override
    public void handle(HubEvent event) {
        //тут будет реализация класса
    }
}
