package ru.yandex.practicum.telemetry.analyzer.repository;

import ru.yandex.practicum.telemetry.analyzer.model.ScenarioAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScenarioActionRepository extends JpaRepository<ScenarioAction, Long> {
}
