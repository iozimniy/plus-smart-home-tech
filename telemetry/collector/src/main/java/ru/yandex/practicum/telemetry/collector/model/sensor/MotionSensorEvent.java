package ru.yandex.practicum.telemetry.collector.model.sensor;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MotionSensorEvent extends SensorEvent {

    @NotNull
    private int linkQuality;

    @NotNull
    private boolean motion;

    @NotNull
    private int voltage;

    @Override
    public SensorEventType getType() {
        return SensorEventType.MOTION_SENSOR_EVENT;
    }
}
