package ru.yandex.practicum.telemetry.collector.service.handler.sensor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEvent;
import ru.yandex.practicum.telemetry.collector.service.handler.mappers.EventMapper;
import ru.yandex.practicum.telemetry.collector.service.kafka.KafkaEventProducer;

import static ru.yandex.practicum.telemetry.collector.service.kafka.EventTopics.SENSORS_EVENTS_TOPIC;

@Slf4j
@RequiredArgsConstructor
public abstract class BaseSensorEventHandler<T extends SpecificRecordBase> implements SensorEventHandler {

    private final KafkaEventProducer producer;
    private final EventMapper mapper;

    @Override
    public void handle(SensorEventProto event) {
        log.info("Processing SensorEvent {}: id={}, hubId={}, timestamp={}", event.getPayloadCase(), event.getId(),
                event.getHubId(), event.getTimestamp());
        log.debug("SensorEvent {}: {}", event.getPayloadCase(), event);

        T payload = mapToAvro(event);
        SensorEventAvro sensorEventAvro = mapper.mapSensorEventToAvro(event, payload);
        producer.send(SENSORS_EVENTS_TOPIC, sensorEventAvro);
        log.info("Sending SensorEvent {} id={}, hubId={}, timestamp={} to topic {}", event.getPayloadCase(), event.getId(),
                event.getHubId(), event.getTimestamp(), SENSORS_EVENTS_TOPIC);
        log.debug("SensorEvent {} for topic {}", event, SENSORS_EVENTS_TOPIC);
    }

    protected abstract T mapToAvro(SensorEventProto event);
}
