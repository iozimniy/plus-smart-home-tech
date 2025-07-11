package ru.yandex.practicum.telemetry.analyzer.kafka.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Getter
@Setter
@ConfigurationProperties("analyzer.kafka")
@Component
public class KafkaConfig implements HubConsumerConfig, SnapshotConsumerConfig {
    private AnalyzerConsumerConfig hubConsumer;
    private AnalyzerConsumerConfig snapshotConsumer;

    @Override
    public Properties getHubConsumerConfig() {
        return hubConsumer.getProperties();
    }

    @Override
    public Properties getSnapshotConsumerConfig() {
        return snapshotConsumer.getProperties();
    }

    @Getter
    @Setter
    public static class AnalyzerConsumerConfig {
        private Properties properties;
    }
}
