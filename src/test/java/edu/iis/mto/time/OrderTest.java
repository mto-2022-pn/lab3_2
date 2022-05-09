package edu.iis.mto.time;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderTest {

    private MockClock clock;
    private Order order;

    @BeforeEach
    void setUp() throws Exception {
        clock = new MockClock();
        order = new Order(clock);
    }

    @Test
    void orderDidntExpired() {
        order.submit();
        clock.setTime(LocalDateTime.now().plusHours(13));
        assertDoesNotThrow(() -> order.confirm());
    }

    @Test
    void orderExpired() {
        order.submit();
        clock.setTime(LocalDateTime.now().plusHours(25));
        assertThrows(OrderExpiredException.class, () -> order.confirm());
    }

}
