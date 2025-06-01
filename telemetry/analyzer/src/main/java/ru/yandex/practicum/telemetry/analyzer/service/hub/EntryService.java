package ru.yandex.practicum.telemetry.analyzer.service.hub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.kafka.telemetry.event.*;
import ru.yandex.practicum.telemetry.analyzer.service.snapshot.SnapshotService;

@Service
@RequiredArgsConstructor
@Slf4j
public class EntryService {

    private final DeviceService deviceService;
    private final ScenarioService scenarioService;

    public void processHubEventAvro(HubEventAvro hubEventAvro) {
        if (hubEventAvro.getPayload() instanceof DeviceAddedEventAvro) {
            deviceService.addDevice(hubEventAvro.getHubId(), (DeviceAddedEventAvro) hubEventAvro.getPayload());
            log.info("Processed DeviceAddedEvent, hubId: {}, id: {}", hubEventAvro.getHubId());
        } else if (hubEventAvro.getPayload() instanceof DeviceRemovedEventAvro) {
            deviceService.removeDevice(hubEventAvro.getHubId(), (DeviceRemovedEventAvro) hubEventAvro.getPayload());
        } else if (hubEventAvro.getPayload() instanceof ScenarioAddedEventAvro) {
            scenarioService.addScenario(hubEventAvro.getHubId(), (ScenarioAddedEventAvro) hubEventAvro.getPayload());
        } else if (hubEventAvro.getPayload() instanceof ScenarioRemovedEventAvro) {
            scenarioService.removeScenario(hubEventAvro.getHubId(),
                    (ScenarioRemovedEventAvro) hubEventAvro.getPayload());
        }
    }
}
