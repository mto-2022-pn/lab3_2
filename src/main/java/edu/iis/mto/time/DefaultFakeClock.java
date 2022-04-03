package edu.iis.mto.time;

import java.time.LocalDateTime;

public class DefaultFakeClock implements FakeClock {
    private LocalDateTime currentTime;
    DefaultFakeClock() {
        currentTime = LocalDateTime.now();
    }
    @Override
    public LocalDateTime now() {
        return currentTime;
    }

    @Override
    public void plusHours(long hours) {
        currentTime = currentTime.plusHours(hours);
    }
}
