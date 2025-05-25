package service.hub;

import lombok.RequiredArgsConstructor;
import model.*;
import model.constants.ConditionOperation;
import model.constants.ConditionType;
import org.springframework.stereotype.Component;
import repository.ConditionRepository;
import repository.SensorRepository;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioConditionAvro;
import service.exceptions.NotFoundException;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ScenarioConditionMapper {

    private final SensorRepository sensorRepository;
    private final ConditionRepository conditionRepository;

    List<ScenarioCondition> mapToModel(ScenarioAddedEventAvro event, Scenario scenario,
                                 String hubId) throws NotFoundException {


        List<ScenarioConditionAvro> avroList = event.getConditions();
        List<ScenarioCondition> conditions = scenario.getConditionList();

        for (var scenarioConditionAvro : avroList) {

            Integer value = mapValue(scenarioConditionAvro);

            Sensor sensor = sensorRepository.findByIdAndHubId(scenarioConditionAvro.getSensorId(), hubId)
                    .orElseThrow(() -> new NotFoundException("Not found sensor with hubId" + hubId + "and id " +
                            scenarioConditionAvro.getSensorId()));

            Condition condition = Condition.builder()
                    .type(scenarioConditionAvro.getType())
                    .operation(scenarioConditionAvro.getOperation())
                    .value(value)
                    .build();

            Long conditionId = conditionRepository.save(condition).getId();


            ScenarioConditionId id = ScenarioConditionId.builder()
                    .conditionId(conditionId)
                    .scenarioId(scenario.getId())
                    .sensorId(sensor.getId())
                    .build();

            ScenarioCondition scenarioCondition = ScenarioCondition.builder()
                    .id(id)
                    .scenario(scenario)
                    .sensor(sensor)
                    .condition(condition)
                    .build();

            conditions.add(scenarioCondition);
        }

        return conditions;
    }

    private Integer mapValue(ScenarioConditionAvro scenario) {
        if (scenario.getValue() != null) {
            if (scenario.getValue() instanceof Boolean bool) {
                return bool ? 1 :0;
            } else if (scenario.getValue() instanceof Integer someInt) {
                return someInt;
            } else {
                throw new IllegalArgumentException("Wrong type of value " + scenario.getValue()
                        .getClass());
            }
        }

        return null;
    }
}
