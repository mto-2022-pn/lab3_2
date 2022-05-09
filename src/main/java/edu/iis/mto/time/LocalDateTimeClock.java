package edu.iis.mto.time;

import java.time.LocalDateTime;

public class  LocalDateTimeClock implements IClock {
    
    @Override
    public LocalDateTime getTime() {
        return LocalDateTime.now();
    }

}
