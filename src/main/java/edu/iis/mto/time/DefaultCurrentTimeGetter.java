package edu.iis.mto.time;

import java.time.LocalDateTime;

public class DefaultCurrentTimeGetter implements CurrentTimeGetter {
    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
