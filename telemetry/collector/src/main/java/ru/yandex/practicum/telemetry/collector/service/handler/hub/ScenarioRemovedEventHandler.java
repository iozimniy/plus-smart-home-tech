package ru.yandex.practicum.telemetry.collector.service.handler.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEventType;

@Component
public class ScenarioRemovedEventHandler extends BaseHubEventHandler {
    @Override
    public HubEventType getMessageType() {
        return HubEventType.SCENARIO_REMOVE;
    }
}
