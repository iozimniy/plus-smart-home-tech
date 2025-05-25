package ru.yandex.practicum.telemetry.aggregator.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;
import ru.yandex.practicum.telemetry.aggregator.service.kafka.config.KafkaClient;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static ru.yandex.practicum.telemetry.aggregator.service.kafka.config.AggregatorTopics.SENSORS_EVENTS_TOPIC;
import static ru.yandex.practicum.telemetry.aggregator.service.kafka.config.AggregatorTopics.SNAPSHOTS_TOPIC;

@Slf4j
@Component
public class AggregatorStarter {
    private final Consumer<String, SpecificRecordBase> consumer;
    private final Producer<String,SpecificRecordBase> producer;
    private final AggregatorState aggregatorState;

    private static final Duration CONSUME_ATTEMPT_TIMEOUT = Duration.ofSeconds(1);

    public AggregatorStarter(AggregatorState aggregatorState, KafkaClient client) {
        this.consumer = client.getConsumer();
        this.producer = client.getProducer();
        this.aggregatorState = aggregatorState;
    }

    public void start() {

        try {
            consumer.subscribe(List.of(SENSORS_EVENTS_TOPIC));

            while (true) {
                ConsumerRecords<String, SpecificRecordBase> records = consumer.poll(CONSUME_ATTEMPT_TIMEOUT);

                for (ConsumerRecord<String, SpecificRecordBase> record : records) {
                    SensorEventAvro sensorEventAvro = (SensorEventAvro) record.value();
                    log.info("Processing event {} from hub {}", sensorEventAvro.getId(), sensorEventAvro.getHubId());
                    log.trace("Processing event {}", sensorEventAvro);
                    Optional<SensorsSnapshotAvro> snapshot = aggregatorState.updateState(sensorEventAvro);

                    if (snapshot.isPresent()) {
                        log.info("Send {}", snapshot);
                        producer.send(new ProducerRecord<>(SNAPSHOTS_TOPIC, null, snapshot.get()));
                    }


                }

                consumer.commitAsync();
            }

        } catch (WakeupException ignored) {
            //игнорируем исключение
        } catch (Exception e) {
            log.error("Error sensor event processing {}", e);
        } finally {
            try {
                producer.flush();
                consumer.commitSync();
            } finally {
                log.info("Закрываем консьюмер");
                consumer.close();
                log.info("Закрываем продюсер");
                producer.close();
            }
        }
    }
}
