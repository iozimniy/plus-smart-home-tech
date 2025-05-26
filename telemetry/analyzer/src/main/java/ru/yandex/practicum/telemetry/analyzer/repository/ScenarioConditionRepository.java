package ru.yandex.practicum.telemetry.analyzer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.telemetry.analyzer.model.ScenarioCondition;

@Repository
public interface ScenarioConditionRepository extends JpaRepository<ScenarioCondition, Long> {
}
