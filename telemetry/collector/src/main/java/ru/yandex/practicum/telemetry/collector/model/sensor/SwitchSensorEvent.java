package ru.yandex.practicum.telemetry.collector.model.sensor;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SwitchSensorEvent extends SensorEvent {

    @NotNull
    private boolean state;

    @Override
    public SensorEventType getType() {
        return SensorEventType.SWITCH_SENSOR_EVENT;
    }
}
