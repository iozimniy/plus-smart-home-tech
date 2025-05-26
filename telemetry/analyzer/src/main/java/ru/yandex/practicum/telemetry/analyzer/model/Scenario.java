package ru.yandex.practicum.telemetry.analyzer.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@Entity
@Table(name = "scenarios")
public class Scenario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hub_id")
    private String hubId;

    private String name;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "scenario_id"
    )
    List<ScenarioCondition> conditionList;

    @OneToMany (
            cascade = CascadeType.ALL,
            mappedBy = "scenario_id"
    )
    List<ScenarioAction> actionList;
}
