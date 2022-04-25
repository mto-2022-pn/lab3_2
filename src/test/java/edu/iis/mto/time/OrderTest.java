package edu.iis.mto.time;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;


class OrderTest {
    static class FakeTimeGetter implements CurrentTimeGetter {
        private LocalDateTime date = LocalDateTime.now();

        @Override
        public LocalDateTime now() {
            return date;
        }

        public void plusHours(long hours) {
            this.date = date.plusHours(hours);
        }
    }

    @BeforeEach
    void setUp() {
    }

    @Test
    void testLessThanValidPeriodTimeConfirmation() {
        FakeTimeGetter timeGetter = new FakeTimeGetter();
        Order order = new Order(timeGetter);
        order.submit();
        timeGetter.plusHours(Order.getValidPeriodHours() - 1);
        order.confirm();
        assertEquals(order.getOrderState(), Order.State.CONFIRMED);
    }

    @Test
    void testOverValidPeriodTimeConfirmation() {
        FakeTimeGetter timeGetter = new FakeTimeGetter();
        Order order = new Order(timeGetter);
        order.submit();
        timeGetter.plusHours(Order.getValidPeriodHours() + 1);
        Assertions.assertThrows(OrderExpiredException.class, order::confirm);
    }
}
