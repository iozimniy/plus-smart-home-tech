package ru.yandex.practicum.telemetry.collector.service.handler.sensor;

import org.apache.avro.specific.SpecificRecordBase;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEvent;

public abstract class BaseSensorEventHandler<T extends SpecificRecordBase> implements SensorEventHandler {
    @Override
    public void handle(SensorEvent event) {
        //тут будет реализация метода отправки сообщения в топик
    }

    protected abstract T mapToAvro(SensorEvent event);
}
