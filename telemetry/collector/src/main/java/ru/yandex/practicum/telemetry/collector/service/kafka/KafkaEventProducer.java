package ru.yandex.practicum.telemetry.collector.service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaEventProducer {
    private final KafkaEventClient client;

    public void send(String topic, SpecificRecordBase event) {
        ProducerRecord<String, SpecificRecordBase> record = new ProducerRecord<>(topic, event);
        client.getProducer().send(record);
        log.trace("Send record {}, event {}", record, event);
    }
}
