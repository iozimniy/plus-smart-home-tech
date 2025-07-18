package ru.yandex.practicum.telemetry.analyzer.service.hub;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.DeviceRemovedEventAvro;
import ru.yandex.practicum.telemetry.analyzer.model.Sensor;
import ru.yandex.practicum.telemetry.analyzer.repository.SensorRepository;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class DeviceService {

    private SensorRepository sensorRepository;

    public void addDevice(String hubId, DeviceAddedEventAvro payload) {
        Sensor newSensor = Sensor.builder()
                .id(payload.getId())
                .hubId(hubId)
                .build();
        sensorRepository.save(newSensor);
        log.info("DeviceAddedEvent with id {} added", payload.getId());
    }

    public void removeDevice(String hubId, DeviceRemovedEventAvro payload) {
        sensorRepository.findByIdAndHubId(payload.getId(), hubId).ifPresent(sensorRepository::delete);
        log.info("DeviceAddedEvent with id {} removed", payload.getId());
    }
}
