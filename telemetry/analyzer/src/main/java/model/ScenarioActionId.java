package model;

import jakarta.persistence.Embeddable;
import lombok.Builder;

@Embeddable
@Builder
public class ScenarioActionId {
    private Long scenarioId;
    private String sensorId;
    private Long actionId;
}
