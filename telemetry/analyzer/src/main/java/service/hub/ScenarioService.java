package service.hub;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import model.Scenario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.ScenarioRepository;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioConditionAvro;

import java.util.ArrayList;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ScenarioService {

    private final ScenarioRepository scenarioRepository;
    private final ScenarioConditionMapper scenarioConditionMapper;

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
    }
}
