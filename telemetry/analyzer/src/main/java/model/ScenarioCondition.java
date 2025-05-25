package model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@Entity
@Table(name = "scenario_conditions")
public class ScenarioCondition {

    @EmbeddedId
    private ScenarioConditionId id;

    @ManyToOne
    @JoinColumn(name = "scenario_id")
    @MapsId
    private Scenario scenario;

    @JoinColumn(name = "sensor_id")
    @MapsId
    @ManyToOne
    private Sensor sensor;

    @ManyToOne
    @JoinColumn(name = "condition_id")
    @MapsId
    private Condition condition;
}
