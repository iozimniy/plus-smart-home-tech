package ru.yandex.practicum.telemetry.aggregator.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorStateAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class AggregatorState {
    private final Map<String, SensorsSnapshotAvro> snapshots = new HashMap<>();

    public Optional<SensorsSnapshotAvro> updateState(SensorEventAvro sensorEvent) {

        if (sensorEvent == null || sensorEvent.getPayload() == null) {
            throw new IllegalArgumentException("Incorrect data: " + sensorEvent);
        } else if (!snapshots.containsKey(sensorEvent.getHubId())) {
            log.info("Create snapshot of new hub {}", sensorEvent.getHubId());
            return createSnapshot(sensorEvent);
        }

        SensorsSnapshotAvro snapshot = snapshots.get(sensorEvent.getHubId());

        if (!snapshot.getSensorsState().containsKey(sensorEvent.getId())) {
            log.info("New sensor {} of hub {}", sensorEvent.getPayload().getClass(), sensorEvent.getHubId());
            return addNewData(snapshot, sensorEvent);
        }

        return updateSnapshot(snapshot, sensorEvent);
    }

    private Optional<SensorsSnapshotAvro> createSnapshot(SensorEventAvro sensorEvent) {

        SensorStateAvro sensorStateAvro = createNewState(sensorEvent);
        Map<String, SensorStateAvro> state = new HashMap<>();
        state.put(sensorEvent.getId(), sensorStateAvro);

        SensorsSnapshotAvro snapshot = SensorsSnapshotAvro.newBuilder()
                .setHubId(sensorEvent.getHubId())
                .setTimestamp(sensorEvent.getTimestamp())
                .setSensorsState(state)
                .build();

        log.trace("NEW SNAPSHOT: {}", snapshot);

        snapshots.put(snapshot.getHubId(), snapshot);

        return Optional.of(snapshot);
    }

    private Optional<SensorsSnapshotAvro> addNewData(SensorsSnapshotAvro snapshot,
                                                     SensorEventAvro sensorEvent) {
        var sensorState = snapshot.getSensorsState();
        sensorState.put(sensorEvent.getId(), createNewState(sensorEvent));
        snapshot.setSensorsState(sensorState);

        log.info("Add new sensor {} type {} for hub {}", sensorEvent.getId(), sensorEvent.getPayload().getClass(),
                sensorEvent.getHubId());
        return Optional.of(snapshot);
    }

    private Optional<SensorsSnapshotAvro> updateSnapshot(SensorsSnapshotAvro snapshot,
                                                         SensorEventAvro sensorEvent) {
        SensorStateAvro oldState = snapshot.getSensorsState().get(sensorEvent.getId());

        if (oldState.getTimestamp().isAfter(sensorEvent.getTimestamp()) ||
                oldState.getData().equals(sensorEvent.getPayload())) {
            log.debug("Not a new state of sensor {}, hub {}", sensorEvent.getId(), sensorEvent.getHubId());
            log.trace("OLD TIME: {}, NEW TIME: {}", oldState.getTimestamp(), sensorEvent.getTimestamp());
            log.trace("OLD: {}, NEW: {}", oldState.getData(), sensorEvent.getPayload());
            return Optional.empty();
        }

        SensorStateAvro newSensorState = createNewState(sensorEvent);
        snapshot.getSensorsState().remove(sensorEvent.getId());
        snapshot.getSensorsState().put(sensorEvent.getId(), newSensorState);
        snapshot.setTimestamp(sensorEvent.getTimestamp());

        log.info("Update state of sensor {}, hub {}", sensorEvent.getId(), sensorEvent.getHubId());
        return Optional.of(snapshot);
    }

    private SensorStateAvro createNewState(SensorEventAvro sensorEvent) {
        return SensorStateAvro.newBuilder()
                .setTimestamp(sensorEvent.getTimestamp())
                .setData(sensorEvent.getPayload())
                .build();
    }
}
