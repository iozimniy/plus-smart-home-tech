package ru.yandex.practicum.telemetry.collector.model.sensor;

import jakarta.validation.constraints.NotNull;

public class SwitchSensorEvent extends SensorEvent {

    @NotNull
    private boolean state;

    @NotNull
    private SensorEventType type;

    @Override
    public SensorEventType getType() {
        return SensorEventType.SWITCH_SENSOR_EVENT;
    }
}
