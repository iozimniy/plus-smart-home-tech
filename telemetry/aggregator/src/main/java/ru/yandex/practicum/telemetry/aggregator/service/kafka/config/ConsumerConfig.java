package ru.yandex.practicum.telemetry.aggregator.service.kafka.config;

import org.springframework.context.annotation.Configuration;

import java.util.Properties;

public interface ConsumerConfig {
    Properties getConsumerProperties();
}
