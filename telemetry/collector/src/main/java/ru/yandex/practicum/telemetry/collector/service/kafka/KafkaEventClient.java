package ru.yandex.practicum.telemetry.collector.service.kafka;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.Producer;

public interface KafkaEventClient {
    Producer<String, SpecificRecordBase> getProducer();

    void stop();
}
