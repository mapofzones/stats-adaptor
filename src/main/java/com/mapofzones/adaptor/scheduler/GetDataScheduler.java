package com.mapofzones.adaptor.scheduler;

import com.mapofzones.adaptor.processor.Processor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GetDataScheduler {

    private final Processor processor;

    public GetDataScheduler(Processor processor) {
        this.processor = processor;
    }

    @Scheduled(fixedDelayString = "${adaptor.sync-time}", initialDelay = 10000)
    public void callDownloader() {
        processor.doScript();
    }
}
