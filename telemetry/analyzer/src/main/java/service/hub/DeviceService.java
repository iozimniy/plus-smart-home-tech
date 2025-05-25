package service.hub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.Sensor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.SensorRepository;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.DeviceRemovedEventAvro;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class DeviceService {

    private final SensorRepository sensorRepository;

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
