package ru.yandex.practicum.telemetry.analyzer.service.snapshot;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequest;
import ru.yandex.practicum.grpc.telemetry.hubrouter.HubRouterControllerGrpc;

import java.util.List;

@Service
@Slf4j
public class ActionClient {

    @GrpcClient("hub-router")
    private HubRouterControllerGrpc.HubRouterControllerBlockingStub actionClient;

    public void send(List<DeviceActionRequest> request) {

        for (DeviceActionRequest deviceActionRequest : request) {
            actionClient.handleDeviceAction(deviceActionRequest);
            log.info("Send action of scenario {} to hub {}", deviceActionRequest.getScenarioName(),
                    deviceActionRequest.getHubId());
        }
    }
}
