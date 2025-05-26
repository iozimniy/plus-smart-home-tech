package ru.yandex.practicum.telemetry.analyzer.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@Entity
@Table(name = "scenarios")
@AllArgsConstructor
@NoArgsConstructor
public class Scenario {

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "scenario"
    )
    List<ScenarioCondition> conditionList;
    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "scenario"
    )
    List<ScenarioAction> actionList;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "hub_id")
    private String hubId;
    private String name;
}
