package ru.yandex.practicum.telemetry.collector.controller;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEvent;
import ru.yandex.practicum.telemetry.collector.service.resolvers.HubEventResolver;
import ru.yandex.practicum.telemetry.collector.service.resolvers.SensorEventResolver;

@RestController
@RequestMapping(path = "/events")
@Validated
public class EventController {

    private final HubEventResolver hubEventResolver;
    private final SensorEventResolver sensorEventResolver;

    public EventController(HubEventResolver hubEventResolver, SensorEventResolver sensorEventResolver) {
        this.hubEventResolver = hubEventResolver;
        this.sensorEventResolver = sensorEventResolver;
    }

    @PostMapping("/hubs")
    public void collectHubEvent(@Valid @RequestBody HubEvent request) {
        hubEventResolver.getHandler(request.getType()).handle(request);
    }

    @PostMapping("/sensors")
    public void collectSensorEvent(@Valid @RequestBody SensorEvent request) {
        sensorEventResolver.getHandler(request.getType()).handle(request);
    }
}
