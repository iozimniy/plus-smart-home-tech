package ru.yandex.practicum.telemetry.collector.model.sensor;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@ToString
public abstract class SensorEvent {
    @NotBlank
    private String id;

    @NotBlank
    private String hubId;
    private Instant timestamp = Instant.now();

    public abstract SensorEventType getType();
}
