package service.snapshot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.Condition;
import model.Scenario;
import model.ScenarioCondition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.ScenarioRepository;
import ru.yandex.practicum.kafka.telemetry.event.*;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SnapshotService {

    private final ScenarioRepository repository;

    public void processSnapshot(SensorsSnapshotAvro snapshot) {
        List<Scenario> scenarios = repository.findByHubId(snapshot.getHubId());

        for (var scenario : scenarios) {
            List<ScenarioCondition> conditions = scenario.getConditionList();

            if (verifyConditions(conditions, snapshot)) {
                //отправляем сценарий на реализацию
            }
        }
    }

    private boolean verifyConditions(List<ScenarioCondition> scenarioConditions, SensorsSnapshotAvro snapshot) {
        Map<String, SensorStateAvro> state = snapshot.getSensorsState();

        for (ScenarioCondition scenarioCondition : scenarioConditions) {

            //если нет сенсора, то и сценарий не реализуется
             if (!state.containsKey(scenarioCondition.getSensor().getId())) {
                 return false;
             }

             Condition condition = scenarioCondition.getCondition();
             var data = state.get(scenarioCondition.getSensor().getId()).getData();

             //в зависимости от типа разные проверки
           return switch (condition.getType()) {
                case MOTION -> checkSensorValue(condition.getValue(), condition.getOperation(),
                        ((MotionSensorAvro) data).getMotion() ? 1 : 0);
                case LUMINOSITY -> checkSensorValue(condition.getValue(), condition.getOperation(), (
                        (LightSensorAvro) data).getLuminosity());
                case SWITCH -> checkSensorValue(condition.getValue(), condition.getOperation(),
                        ((SwitchSensorAvro) data).getState() ? 1 : 0);
                case TEMPERATURE -> checkTemperature(condition.getValue(), condition.getOperation(), data);
                case CO2LEVEL -> checkSensorValue(condition.getValue(), condition.getOperation(),
                        ((ClimateSensorAvro) data).getCo2Level());
                case HUMIDITY -> checkSensorValue(condition.getValue(), condition.getOperation(),
                        ((ClimateSensorAvro) data).getHumidity());
            };
        }

        return false;
    }

    //сверяем значения с условием

    private boolean checkSensorValue(Integer condition, ConditionOperationAvro operation, Integer value) {
        return switch (operation) {
            case EQUALS -> value == condition;
            case GREATER_THAN -> value > condition;
            case LOWER_THAN -> value < condition;
        };
    }

    //температуру получаем от двух сенсоров
    private boolean checkTemperature(Integer condition, ConditionOperationAvro operation, Object data) {

        if (data instanceof ClimateSensorAvro) {
            return checkSensorValue(condition, operation,
                    ((ClimateSensorAvro) data).getTemperatureC());
        } else if (data instanceof TemperatureSensorAvro) {
            return checkSensorValue(condition, operation,
                    ((TemperatureSensorAvro) data).getTemperatureC());
        } else {
            throw new IllegalArgumentException("Wrong sensor type " + data.getClass());
        }
    }

}
