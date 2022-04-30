package edu.iis.mto.time;

import java.time.LocalDateTime;

public class DefaultClock implements Clock {
    @Override
    public LocalDateTime getTime() {
        return LocalDateTime.now();
    }
}
