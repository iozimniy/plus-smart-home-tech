package ru.yandex.practicum.telemetry.aggregator.service.kafka.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Properties;

@ConfigurationProperties("aggregator.kafka")
public class KafkaConfig implements ConsumerConfig, ProducerConfig {

    private AggregatorConsumerConfig consumer;
    private AggregatorProducerConfig producer;

    @Override
    public Properties getConsumerProperties() {
        return consumer.getProperties();
    }

    @Override
    public Properties getProducerProperties() {
        return producer.getProperties();
    }

    @Getter
    public static class AggregatorConsumerConfig {
        private final Properties properties;

        public AggregatorConsumerConfig(Properties properties) {
            this.properties = properties;
        }

    }

    @Getter
    public static class AggregatorProducerConfig {
        private final Properties properties;

        public AggregatorProducerConfig(Properties properties) {
            this.properties = properties;
        }

    }

}
