package kafka;

import kafka.configuration.HubConsumerConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import service.EntryService;

import java.time.Duration;
import java.util.List;

import static kafka.KafkaTopics.HUB_TOPIC;

@Component
@Slf4j
public class HubEventProcessor implements Runnable {

    private final Consumer<String, SpecificRecordBase> consumer;

    private final Duration CONSUME_ATTEMPT_TIMEOUT = Duration.ofMillis(100);

    private final EntryService service;

    public HubEventProcessor(HubConsumerConfig config, EntryService service) {
        this.consumer = new KafkaConsumer<>(config.getHubConsumerConfig());
        this.service = service;
    }

    @Override
    public void run() {
        try {
            consumer.subscribe(List.of(HUB_TOPIC));

            while (true) {
                ConsumerRecords<String, SpecificRecordBase> records = consumer.poll(CONSUME_ATTEMPT_TIMEOUT);

                for (ConsumerRecord<String, SpecificRecordBase> record : records) {
                    HubEventAvro hubEventAvro = (HubEventAvro) record.value();
                    service.processHubEventAvro(hubEventAvro);
                    log.info("Coming HubEvent from hub {}", hubEventAvro.getHubId());
                }
            }

        } catch (WakeupException e) {
            // тут ничего не делаем
        } catch (Exception e) {
            log.error("Error hun event processing: {}", e);
        } finally {
            try {
                consumer.commitSync();
            } finally {
                log.info("Close hubConsumer");
                consumer.close();
            }
        }
    }
}
