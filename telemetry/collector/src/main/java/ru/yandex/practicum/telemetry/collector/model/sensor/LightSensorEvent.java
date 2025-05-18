package ru.yandex.practicum.telemetry.collector.model.sensor;

import lombok.Getter;

@Getter
public class LightSensorEvent extends SensorEvent {

    private int linkQuality;

    private int luminosity;

    private SensorEventType type;

    @Override
    public SensorEventType getType() {
        return SensorEventType.LIGHT_SENSOR_EVENT;
    }
}
