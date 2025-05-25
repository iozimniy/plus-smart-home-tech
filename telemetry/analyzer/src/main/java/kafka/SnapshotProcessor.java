package kafka;

import kafka.configuration.SnapshotConsumerConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.stereotype.Component;

import java.util.List;

import static kafka.KafkaTopics.SNAPSHOT_TOPIC;

@Component
@Slf4j
public class SnapshotProcessor {

    private final Consumer<String, SpecificRecordBase> consumer;

    public SnapshotProcessor(SnapshotConsumerConfig config) {
        this.consumer = new KafkaConsumer<>(config.getSnapshotConsumerConfig());
    }

    public void start() {
        try {
            consumer.subscribe(List.of(SNAPSHOT_TOPIC));


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
