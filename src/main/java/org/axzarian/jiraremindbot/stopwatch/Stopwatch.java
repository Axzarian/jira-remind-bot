package org.axzarian.jiraremindbot.stopwatch;

import java.time.Duration;
import java.time.Instant;
import org.springframework.stereotype.Component;

@Component
public class Stopwatch {

    private Instant start = Instant.now();

    public long getTime() {
        return Duration.between(start, Instant.now()).toSeconds();
    }

    public void reset() {
        this.start = Instant.now();
    }
}
