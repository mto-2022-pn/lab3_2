package edu.iis.mto.time;

import java.time.LocalDateTime;

public class MockClock implements IClock {

    LocalDateTime time;

    MockClock() {
        time = LocalDateTime.now();
    }

    @Override
    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

}
