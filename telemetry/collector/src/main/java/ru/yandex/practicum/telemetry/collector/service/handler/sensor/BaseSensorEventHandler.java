package ru.yandex.practicum.telemetry.collector.service.handler.sensor;

import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEvent;
import ru.yandex.practicum.telemetry.collector.service.handler.mappers.EventMapper;
import ru.yandex.practicum.telemetry.collector.service.kafka.KafkaEventProducer;

import static ru.yandex.practicum.telemetry.collector.service.kafka.EventTopics.SENSORS_EVENTS_TOPIC;

@RequiredArgsConstructor
public abstract class BaseSensorEventHandler<T extends SpecificRecordBase> implements SensorEventHandler {

    private final KafkaEventProducer producer;
    private EventMapper mapper;

    @Override
    public void handle(SensorEvent event) {
        T payload = mapToAvro(event);
        SensorEventAvro sensorEventAvro = mapper.mapSensorEventToAvro(event, payload);
        producer.send(SENSORS_EVENTS_TOPIC, sensorEventAvro);
    }

    protected abstract T mapToAvro(SensorEvent event);
}
