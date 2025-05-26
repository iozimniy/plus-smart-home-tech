package ru.yandex.practicum.telemetry.analyzer.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class ScenarioConditionId {
    private Long scenarioId;
    private String sensorId;
    private Long conditionId;
}
