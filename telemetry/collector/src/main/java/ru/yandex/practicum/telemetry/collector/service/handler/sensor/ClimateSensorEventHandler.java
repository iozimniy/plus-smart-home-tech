package ru.yandex.practicum.telemetry.collector.service.handler.sensor;

import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.ClimateSensorAvro;
import ru.yandex.practicum.telemetry.collector.model.sensor.ClimateSensorEvent;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEvent;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEventType;

@Component
public class ClimateSensorEventHandler extends BaseSensorEventHandler {

    @Override
    public SensorEventType getMessageType() {
        return SensorEventType.CLIMATE_SENSOR_EVENT;
    }

    @Override
    protected ClimateSensorAvro mapToAvro(SensorEvent event) {
        ClimateSensorEvent realEvent = (ClimateSensorEvent) event;

        return ClimateSensorAvro.newBuilder()
                .setTemperatureC(realEvent.getTemperatureC())
                .setHumidity(realEvent.getHumidity())
                .setCo2Level(realEvent.getCo2Level())
                .build();
    }
}
