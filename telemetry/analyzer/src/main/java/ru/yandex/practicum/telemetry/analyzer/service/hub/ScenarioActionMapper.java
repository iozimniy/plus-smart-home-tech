package ru.yandex.practicum.telemetry.analyzer.service.hub;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.DeviceActionAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;
import ru.yandex.practicum.telemetry.analyzer.model.*;
import ru.yandex.practicum.telemetry.analyzer.repository.SensorRepository;
import ru.yandex.practicum.telemetry.analyzer.service.exceptions.NotFoundException;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ScenarioActionMapper {

    private final SensorRepository sensorRepository;
    private final ru.yandex.practicum.telemetry.analyzer.repository.ActionRepository actionRepository;

    public List<ScenarioAction> mapToModel(ScenarioAddedEventAvro event, Scenario scenario,
                                           String hubId) throws NotFoundException {

        List<DeviceActionAvro> avroList = event.getActions();

        List<ScenarioAction> actions = scenario.getActionList();

        for (var deviceActionAvro : avroList) {
            Action action = Action.builder()
                    .type(deviceActionAvro.getType())
                    .value(deviceActionAvro.getValue())
                    .build();

            Sensor sensor = sensorRepository.findByIdAndHubId(deviceActionAvro.getSensorId(), hubId)
                    .orElseThrow(() -> new NotFoundException("Not found sensor with hubId" + hubId + "and id " +
                            deviceActionAvro.getSensorId()));

            Long actionId = actionRepository.save(action).getId();

            ScenarioActionId id = ScenarioActionId.builder()
                    .actionId(actionId)
                    .sensorId(sensor.getId())
                    .scenarioId(scenario.getId())
                    .build();

            ScenarioAction scenarioAction = ScenarioAction.builder()
                    .id(id)
                    .action(action)
                    .scenario(scenario)
                    .sensor(sensor)
                    .build();

            actions.add(scenarioAction);
        }

        return actions;

    }
}
