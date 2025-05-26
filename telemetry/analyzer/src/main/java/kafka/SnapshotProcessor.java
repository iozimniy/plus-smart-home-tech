package kafka;

import kafka.configuration.SnapshotConsumerConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;
import service.EntryService;

import java.time.Duration;
import java.util.List;

import static kafka.KafkaTopics.SNAPSHOT_TOPIC;

@Component
@Slf4j
public class SnapshotProcessor {

    private final Consumer<String, SpecificRecordBase> consumer;

    private final Duration CONSUME_ATTEMPT_TIMEOUT = Duration.ofMillis(100);

    private final EntryService service;

    public SnapshotProcessor(SnapshotConsumerConfig config, EntryService service) {
        this.consumer = new KafkaConsumer<>(config.getSnapshotConsumerConfig());
        this.service = service;
    }

    public void start() {
        try {
            consumer.subscribe(List.of(SNAPSHOT_TOPIC));

            ConsumerRecords<String, SpecificRecordBase> records = consumer.poll(CONSUME_ATTEMPT_TIMEOUT);
            for (ConsumerRecord<String, SpecificRecordBase> record : records) {
                SensorsSnapshotAvro snapshot = (SensorsSnapshotAvro) record.value();
                service.processSensorsSnapshotAvro(snapshot);
                log.info("Coming SensorsSnapshot from hub ()", snapshot.getHubId());
            }


        } catch (WakeupException e) {
            // тут ничего не делаем
        } catch (Exception e) {
            log.error("Error snapshot processing: {}", e);
        } finally {
            try {
                consumer.commitSync();
            } finally {
                log.info("Close snapshot consumer");
                consumer.close();
            }
        }
    }
}
