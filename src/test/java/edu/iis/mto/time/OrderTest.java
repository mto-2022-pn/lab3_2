package edu.iis.mto.time;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;


class OrderTest {

    private static final long VALID_PERIOD_HOURS = 24;
    private Order order;
    private MockUpClock clock = new MockUpClock();

    @BeforeEach
    void setUp() throws Exception {
        clock.setTime(LocalDateTime.now());
        order = new Order(clock);
    }

    @Test
    void orderNotExpiredTest() {
        order.submit();
        clock.setTime(LocalDateTime.now().plusHours(VALID_PERIOD_HOURS - 1));
        assertDoesNotThrow(() -> order.confirm());
    }

    @Test
    void orderExpiredTest() {
        order.submit();
        clock.setTime(LocalDateTime.now().plusHours(VALID_PERIOD_HOURS));
        assertThrows(OrderExpiredException.class, () -> order.confirm());
    }

    @Test
    void orderExpiredOneHourTest() {
        order.submit();
        clock.setTime(LocalDateTime.now().plusHours(VALID_PERIOD_HOURS + 1));
        assertThrows(OrderExpiredException.class, () -> order.confirm());
    }
}
