package ru.yandex.practicum.telemetry.collector.model.sensor;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class TemperatureSensorEvent extends SensorEvent {

    @NotNull
    private int temperatureC;

    @NotNull
    private int temperatureF;

    @NotNull
    private SensorEventType type;

    @Override
    public SensorEventType getType() {
        return SensorEventType.TEMPERATURE_SENSOR_EVENT;
    }
}
