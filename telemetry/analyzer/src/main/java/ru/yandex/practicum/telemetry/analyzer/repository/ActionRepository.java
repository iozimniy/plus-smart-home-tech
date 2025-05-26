package ru.yandex.practicum.telemetry.analyzer.repository;

import ru.yandex.practicum.telemetry.analyzer.model.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {
}
