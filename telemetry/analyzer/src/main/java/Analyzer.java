import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@ConfigurationPropertiesScan
@EnableJpaRepositories("ru.yandex.practicum.telemetry.analyzer.repository")
@EntityScan("ru.yandex.practicum.telemetry.analyzer.model")
@EnableAutoConfiguration()
@SpringBootApplication(scanBasePackages = "ru.yandex.practicum.telemetry.analyzer")
public class Analyzer {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Analyzer.class, args);
    }
}
