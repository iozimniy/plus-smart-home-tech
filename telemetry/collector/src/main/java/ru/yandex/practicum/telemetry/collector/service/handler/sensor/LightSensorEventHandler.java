package ru.yandex.practicum.telemetry.collector.service.handler.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.LightSensorProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.LightSensorAvro;
import ru.yandex.practicum.telemetry.collector.model.sensor.LightSensorEvent;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEvent;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEventType;
import ru.yandex.practicum.telemetry.collector.service.handler.mappers.EventMapper;
import ru.yandex.practicum.telemetry.collector.service.kafka.KafkaEventProducer;

@Component
public class LightSensorEventHandler extends BaseSensorEventHandler {

    public LightSensorEventHandler(KafkaEventProducer producer, EventMapper mapper) {
        super(producer, mapper);
    }

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.LIGHT_SENSOR_EVENT;
    }

    @Override
    protected LightSensorAvro mapToAvro(SensorEventProto event) {
        LightSensorProto realEvent = event.getLightSensorEvent();

        return LightSensorAvro.newBuilder()
                .setLinkQuality(realEvent.getLinkQuality())
                .setLuminosity(realEvent.getLuminosity())
                .build();
    }
}
