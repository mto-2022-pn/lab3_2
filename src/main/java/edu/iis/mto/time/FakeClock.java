package edu.iis.mto.time;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class FakeClock implements Clock {
    private LocalDateTime time = LocalDateTime.now();

    @Override
    public LocalDateTime getTime() {
        return time;
    }
    public void plusHours(int hours) {
        time = time.plusHours(hours);
    }
}
