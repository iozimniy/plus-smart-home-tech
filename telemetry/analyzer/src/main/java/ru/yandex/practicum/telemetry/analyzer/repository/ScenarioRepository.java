package ru.yandex.practicum.telemetry.analyzer.repository;

import ru.yandex.practicum.telemetry.analyzer.model.Scenario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScenarioRepository extends JpaRepository<Scenario, Long> {
    Optional<Scenario> findByHubIdAndName(String hubId, String name);

    List<Scenario> findByHubId(String hubId);
}
