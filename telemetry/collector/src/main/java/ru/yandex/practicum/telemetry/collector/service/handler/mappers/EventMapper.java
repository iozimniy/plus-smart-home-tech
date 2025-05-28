package ru.yandex.practicum.telemetry.collector.service.handler.mappers;

import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEvent;

import com.google.protobuf.Timestamp;
import java.time.Instant;

@Component
public class EventMapper<T extends SpecificRecordBase> {

    public HubEventAvro mapHubEventToAvro(HubEventProto event, T payload) {



        return HubEventAvro.newBuilder()
                .setHubId(event.getHubId())
                .setTimestamp(getInstant(event.getTimestamp()))
                .setPayload(payload)
                .build();
    }

    public SensorEventAvro mapSensorEventToAvro(SensorEventProto event, T payload) {

        return SensorEventAvro.newBuilder()
                .setHubId(event.getHubId())
                .setId(event.getId())
                .setTimestamp(getInstant(event.getTimestamp()))
                .setPayload(payload)
                .build();
    }

    private Instant getInstant(Timestamp timestamp) {
        return Instant.ofEpochSecond(timestamp.getSeconds(),
               timestamp.getNanos());
    }
}
