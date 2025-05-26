package ru.yandex.practicum.telemetry.analyzer.service.hub;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioRemovedEventAvro;
import ru.yandex.practicum.telemetry.analyzer.model.Scenario;
import ru.yandex.practicum.telemetry.analyzer.repository.ScenarioRepository;
import ru.yandex.practicum.telemetry.analyzer.service.exceptions.NotFoundException;

import java.util.ArrayList;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class ScenarioService {

    private ScenarioRepository scenarioRepository;
    private ScenarioConditionMapper scenarioConditionMapper;
    private ScenarioActionMapper scenarioActionMapper;

    @SneakyThrows
    public void addScenario(String hubId, ScenarioAddedEventAvro payload) {
        Scenario newScenario = Scenario.builder()
                .hubId(hubId)
                .name(payload.getName())
                .conditionList(new ArrayList<>())
                .actionList(new ArrayList<>())
                .build();
        Scenario scenario = scenarioRepository.save(newScenario);

        scenario.setConditionList(scenarioConditionMapper.mapToModel(payload, scenario, hubId));
        scenario.setActionList(scenarioActionMapper.mapToModel(payload, scenario, hubId));

        scenarioRepository.save(scenario);
        log.info("Added scenario with name {}", scenario.getName());
    }

    @SneakyThrows
    public void removeScenario(String hubId, ScenarioRemovedEventAvro payload) {
        Scenario scenario = scenarioRepository.findByHubIdAndName(hubId, payload.getName())
                .orElseThrow(() -> new NotFoundException("Not found scenario with hubId " + hubId
                        + " and name " + payload.getName()));
        scenarioRepository.delete(scenario);
    }
}
