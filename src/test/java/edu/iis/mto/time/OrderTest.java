package edu.iis.mto.time;

import org.junit.jupiter.api.Test;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderTest {

    private Clock getClockMockWithTimeWithAddedOffset(Duration offsetDuration) {
        Clock clock = mock(Clock.class);
        Instant submitTime = Instant.now();
        Instant confirmTime = submitTime.plus(offsetDuration);
        when(clock.instant()).thenReturn(submitTime, confirmTime);
        return clock;
    }

    @Test
    public void orderNotExpiredInstantly() {
        Clock clock = getClockMockWithTimeWithAddedOffset(Duration.ofHours(0));
        Order order = new Order(clock);
        order.submit();
        assertDoesNotThrow(order::confirm);
    }

    @Test
    public void orderNotExpiredAfterHoursLessThanValidPeriod() {
        Clock clock = getClockMockWithTimeWithAddedOffset(Duration.ofHours(8));
        Order order = new Order(clock);
        order.submit();
        assertDoesNotThrow(order::confirm);
    }

    @Test
    public void orderNotExpiredOnValidPeriod() {
        Clock clock = getClockMockWithTimeWithAddedOffset(Duration.ofHours(24));
        Order order = new Order(clock);
        order.submit();
        assertDoesNotThrow(order::confirm);
    }

    @Test
    public void orderExpiredOnHoursMoreThanValidPeriod() {
        Clock clock = getClockMockWithTimeWithAddedOffset(Duration.ofHours(25));
        Order order = new Order(clock);
        order.submit();
        assertThrows(OrderExpiredException.class, order::confirm);
    }

}
