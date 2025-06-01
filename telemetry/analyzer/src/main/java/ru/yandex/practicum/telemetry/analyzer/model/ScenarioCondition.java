package ru.yandex.practicum.telemetry.analyzer.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@Entity
@Table(name = "scenario_conditions")
@AllArgsConstructor
@NoArgsConstructor
public class ScenarioCondition {

    @EmbeddedId
    private ScenarioConditionId id;

    @ManyToOne
    @JoinColumn(name = "scenario_id")
    @MapsId("scenarioId")
    private Scenario scenario;

    @JoinColumn(name = "sensor_id")
    @MapsId("sensorId")
    @ManyToOne
    private Sensor sensor;

    @ManyToOne
    @JoinColumn(name = "condition_id")
    @MapsId("conditionId")
    private Condition condition;
}
