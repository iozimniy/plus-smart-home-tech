package ru.yandex.practicum.telemetry.aggregator.service.kafka.config;

import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaAggregatorConfig implements KafkaClient {

    private final ConsumerConfig consumerConfig;
    private final ProducerConfig producerConfig;

    @Override
    public Consumer<String, SpecificRecordBase> getConsumer() {
        return new KafkaConsumer<>(consumerConfig.getConsumerProperties());
    }

    @Override
    public Producer<String, SpecificRecordBase> getProducer() {
        return new KafkaProducer<>(producerConfig.getProducerProperties());
    }
}
