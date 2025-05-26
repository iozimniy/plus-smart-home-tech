package ru.yandex.practicum.telemetry.analyzer.service.snapshot;

import com.google.protobuf.Timestamp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.telemetry.analyzer.model.Condition;
import ru.yandex.practicum.telemetry.analyzer.model.Scenario;
import ru.yandex.practicum.telemetry.analyzer.model.ScenarioAction;
import ru.yandex.practicum.telemetry.analyzer.model.ScenarioCondition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.telemetry.analyzer.repository.ScenarioRepository;
import ru.yandex.practicum.grpc.telemetry.event.ActionTypeProto;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionProto;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequest;
import ru.yandex.practicum.kafka.telemetry.event.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SnapshotService {

    private final ScenarioRepository repository;
    private final ActionClient actionClient;

    public void processSnapshot(SensorsSnapshotAvro snapshot) {
        List<Scenario> scenarios = repository.findByHubId(snapshot.getHubId());

        for (var scenario : scenarios) {
            List<ScenarioCondition> conditions = scenario.getConditionList();

            if (verifyConditions(conditions, snapshot)) {
                //отправляем сценарий на реализацию
                actionClient.send(getRequest(scenario, snapshot));
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

    private List<DeviceActionRequest> getRequest(Scenario scenario, SensorsSnapshotAvro snapshot) {

        List<ScenarioAction> actionList = scenario.getActionList();

        List<DeviceActionRequest> requestList = new ArrayList<>();

        for (var scenarioAction : actionList) {
            DeviceActionProto deviceActionProto = DeviceActionProto.newBuilder()
                    .setSensorId(scenarioAction.getSensor().getId())
                    .setType(ActionTypeProto.valueOf(scenarioAction.getAction().getType().toString()))
                    .setValue(scenarioAction.getAction().getValue())
                    .build();

            DeviceActionRequest request = DeviceActionRequest.newBuilder()
                    .setHubId(snapshot.getHubId())
                    .setScenarioName(scenario.getName())
                    .setAction(deviceActionProto)
                    .setTimestamp(createTimestamp(snapshot))
                    .build();

            requestList.add(request);

        }

        return requestList;
    }

    private Timestamp createTimestamp(SensorsSnapshotAvro sensor) {
        Instant timestampAvro = sensor.getTimestamp();

        return Timestamp.newBuilder()
                .setSeconds(timestampAvro.getEpochSecond())
                .setNanos(timestampAvro.getNano())
                .build();
    }

}
