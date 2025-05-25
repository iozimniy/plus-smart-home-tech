package kafka.configuration;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Properties;

@ConfigurationProperties("analyzer.kafka")
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
    public static class AnalyzerConsumerConfig {
        private Properties properties;
    }
}
