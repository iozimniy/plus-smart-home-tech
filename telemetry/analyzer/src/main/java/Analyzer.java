import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@ConfigurationPropertiesScan
@ComponentScan("ru.yandex.practicum.telemetry.analyzer")
@EnableJpaRepositories("ru.yandex.practicum.telemetry.analyzer.repository")
@EntityScan("ru.yandex.practicum.telemetry.analyzer.model")
@SpringBootApplication
public class Analyzer {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Analyzer.class, args);
    }
}
