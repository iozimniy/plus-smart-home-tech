package model;

import jakarta.persistence.Embeddable;
import lombok.Builder;

@Builder
@Embeddable
public class ScenarioConditionId {
    private Long scenarioId;
    private String sensorId;
    private Long conditionId;
}
