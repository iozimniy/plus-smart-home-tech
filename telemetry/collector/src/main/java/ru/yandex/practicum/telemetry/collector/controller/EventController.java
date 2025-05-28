package ru.yandex.practicum.telemetry.collector.controller;

import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.yandex.practicum.grpc.telemetry.collector.CollectorControllerGrpc;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
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

        try {
            hubEventResolver.getHandler(request.getPayloadCase()).handle(request);
            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(new StatusRuntimeException(
                    Status.INTERNAL
                            .withDescription(e.getLocalizedMessage())
                            .withCause(e)
            ));
        }
    }

    @Override
    public void collectSensorEvent(SensorEventProto request, StreamObserver<Empty> responseObserver) {
        try {
            sensorEventResolver.getHandler(request.getPayloadCase()).handle(request);
            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(new StatusRuntimeException(
                    Status.INTERNAL
                            .withDescription(e.getLocalizedMessage())
                            .withCause(e)
            ));
        }

    }
}
