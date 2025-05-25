package repository;

import model.Scenario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScenarioRepository extends JpaRepository<Scenario, Long> {
}
