package edu.iis.mto.time;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class FakeClock {
    private LocalDateTime time = LocalDateTime.now();

    public LocalDateTime getTime() {
        return time;
    }
    public void plusHours(int hours) {
        time = time.plusHours(hours);
    }
}
