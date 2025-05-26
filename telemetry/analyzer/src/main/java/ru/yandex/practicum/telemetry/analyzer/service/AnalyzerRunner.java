package ru.yandex.practicum.telemetry.analyzer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.telemetry.analyzer.kafka.HubEventProcessor;
import ru.yandex.practicum.telemetry.analyzer.kafka.SnapshotProcessor;

@Component
@RequiredArgsConstructor
@Slf4j
public class AnalyzerRunner implements CommandLineRunner {

    final HubEventProcessor hubEventProcessor;

    final SnapshotProcessor snapshotProcessor;

    @Override
    public void run(String... args) throws Exception {
        log.trace("Running Analyzer");
        Thread hubEventsThread = new Thread(hubEventProcessor);
        hubEventsThread.setName("HubEventHandlerThread");
        hubEventsThread.start();
        log.trace("hubEventProcessor started");

        snapshotProcessor.start();
        log.trace("snapshotProcessor started");
    }
}
