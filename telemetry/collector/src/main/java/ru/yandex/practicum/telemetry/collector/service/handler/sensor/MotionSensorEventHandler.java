package ru.yandex.practicum.telemetry.collector.service.handler.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.MotionSensorProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.MotionSensorAvro;
import ru.yandex.practicum.telemetry.collector.model.sensor.MotionSensorEvent;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEvent;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEventType;
import ru.yandex.practicum.telemetry.collector.service.handler.mappers.EventMapper;
import ru.yandex.practicum.telemetry.collector.service.kafka.KafkaEventProducer;

@Component
public class MotionSensorEventHandler extends BaseSensorEventHandler {

    public MotionSensorEventHandler(KafkaEventProducer producer, EventMapper mapper) {
        super(producer, mapper);
    }

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.MOTION_SENSOR_EVENT;
    }

    @Override
    protected MotionSensorAvro mapToAvro(SensorEventProto event) {
        MotionSensorProto realEvent = event.getMotionSensorEvent();

        return MotionSensorAvro.newBuilder()
                .setLinkQuality(realEvent.getLinkQuality())
                .setMotion(realEvent.getMotion())
                .setVoltage(realEvent.getVoltage())
                .build();
    }
}
