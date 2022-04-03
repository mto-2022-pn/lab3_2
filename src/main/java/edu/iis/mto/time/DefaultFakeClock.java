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
    public void changeHours(int hours) {
        if (hours > 0)
            currentTime = currentTime.plusHours(hours);
        currentTime = currentTime.minusHours(-hours);
    }
}
