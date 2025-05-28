package ru.yandex.practicum.telemetry.analyzer.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Setter
@Getter
@Table(name = "scenario_actions")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ScenarioAction {

    @EmbeddedId
    private ScenarioActionId id;

    @ManyToOne
    @JoinColumn(name = "scenario_id")
    @MapsId("scenarioId")
    private Scenario scenario;

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    @MapsId("sensorId")
    private Sensor sensor;

    @ManyToOne
    @JoinColumn(name = "action_id")
    @MapsId("actionId")
    private Action action;
}
