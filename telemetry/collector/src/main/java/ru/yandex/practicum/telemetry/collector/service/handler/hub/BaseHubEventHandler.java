package ru.yandex.practicum.telemetry.collector.service.handler.hub;

import org.apache.avro.specific.SpecificRecordBase;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEvent;

public abstract class BaseHubEventHandler<T extends SpecificRecordBase> implements HubEventHandler{

    @Override
    public void handle(HubEvent event) {
        //тут будет реализация класса
    }

    protected abstract T mapToAvro(HubEvent event);
}
