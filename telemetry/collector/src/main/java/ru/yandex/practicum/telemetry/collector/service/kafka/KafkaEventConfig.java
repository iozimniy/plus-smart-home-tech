package ru.yandex.practicum.telemetry.collector.service.kafka;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.VoidSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.kafka.serializer.GeneralAvroSerializer;

import java.util.Properties;

@Configuration
public class KafkaEventConfig {

    @Bean
    KafkaEventClient getClient() {
        return new KafkaEventClient() {
            private Producer<String, SpecificRecordBase> producer;

            @Override
            public Producer<String, SpecificRecordBase> getProducer() {
                if (producer == null) {
                    initProducer();
                }

                return producer;
            }

            @Override
            public void stop() {
                if (producer != null) {
                    producer.close();
                }
            }

            private void initProducer() {
                Properties properties = new Properties();

                properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
                properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, VoidSerializer.class);
                properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GeneralAvroSerializer.class);

                producer = new KafkaProducer<>(properties);
            }
        };
    }
}
