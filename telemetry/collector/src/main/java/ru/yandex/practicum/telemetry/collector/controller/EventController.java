package ru.yandex.practicum.telemetry.collector.controller;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import jakarta.validation.Valid;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.grpc.telemetry.collector.CollectorControllerGrpc;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEventType;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEvent;
import ru.yandex.practicum.telemetry.collector.service.resolvers.HubEventResolver;
import ru.yandex.practicum.telemetry.collector.service.resolvers.SensorEventResolver;

@GrpcService
public class EventController extends CollectorControllerGrpc.CollectorControllerImplBase {

    private final HubEventResolver hubEventResolver;
    private final SensorEventResolver sensorEventResolver;

    public EventController(HubEventResolver hubEventResolver, SensorEventResolver sensorEventResolver) {
        this.hubEventResolver = hubEventResolver;
        this.sensorEventResolver = sensorEventResolver;
    }

    @Override
    public void collectHubEvent(HubEventProto request, StreamObserver<Empty> responseObserver) {
        hubEventResolver.getHandler(HubEventType.valueOf(request.getPayloadCase().toString())).handle(request);
    }

    @Override
    public void collectSensorEvent() {
        sensorEventResolver.getHandler(request.getType()).handle(request);
    }
}
