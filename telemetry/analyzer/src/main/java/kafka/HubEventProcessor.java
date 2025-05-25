package kafka;

import kafka.configuration.HubConsumerConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.stereotype.Component;

import java.util.List;

import static kafka.KafkaTopics.HUB_TOPIC;

@Component
@Slf4j
public class HubEventProcessor implements Runnable {

    private final Consumer<String, SpecificRecordBase> consumer;

    public HubEventProcessor(HubConsumerConfig config) {
        this.consumer = new KafkaConsumer<>(config.getHubConsumerConfig());
    }

    @Override
    public void run() {
        try {
            consumer.subscribe(List.of(HUB_TOPIC));

            while (true) {

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
