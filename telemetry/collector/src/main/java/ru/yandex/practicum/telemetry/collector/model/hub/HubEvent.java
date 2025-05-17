package ru.yandex.practicum.telemetry.collector.model.hub;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@ToString
public abstract class HubEvent {

    @NotBlank
    private String hubId;
    private Instant timestamp = Instant.now();

    public abstract HubEventType getType();
}
