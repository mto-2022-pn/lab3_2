package edu.iis.mto.time;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;


class OrderTest {

    @BeforeEach
    void setUp() throws Exception {

    }

    @Test
    void test() {
        FakeClock fakeClock = new FakeClock();
        Order order = new Order(fakeClock);

        order.submit();
        fakeClock.plusHours(25);

        assertThrows(OrderExpiredException.class, order::confirm);
        assertEquals(Order.State.CANCELLED, order.getOrderState());
    }

    @Test
    void test2() {
        Order order = new Order();

        order.submit();
        order.confirm();
        order.realize();

        assertEquals(Order.State.REALIZED, order.getOrderState());
    }

    @Test
    void test3() {
        FakeClock fakeClock = new FakeClock();
        Order order = new Order(fakeClock);

        order.submit();
        fakeClock.plusHours(23);
        order.confirm();
        order.realize();

        assertEquals(Order.State.REALIZED, order.getOrderState());
    }

    @Test
    void testConfirmThrowsExceptionForCreatedState() {
        Order order = new Order();

        assertThrows(OrderStateException.class, order::confirm);
    }

    @Test
    void testRealizeThrowsExceptionForCreatedState() {
        Order order = new Order();

        assertThrows(OrderStateException.class, order::realize);
    }

    @Test
    void testConfirmThrowsExceptionForSubmittedState() {
        Order order = new Order();

        order.submit();

        assertThrows(OrderStateException.class, order::realize);
    }
}
