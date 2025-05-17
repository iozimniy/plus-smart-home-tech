package ru.yandex.practicum.telemetry.collector.service.handler.sensor;

import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEvent;

public abstract class BaseSensorEventHandler implements SensorEventHandler {
    @Override
    public void handle(SensorEvent event) {
        //тут будет реализация метода отправки сообщения в топик
    }
}
