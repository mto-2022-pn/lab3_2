package edu.iis.mto.time;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class OrderTest {
    private static final long VALID_PERIOD_HOURS = 24;
    @Test
    void testOrderRealizedInValidHours() {
        Order order = new Order(new DefaultFakeClock());
        order.addItem(new OrderItem());
        order.submit();
        order.confirm();
        order.getFakeClock().plusHours(5);
        order.realize();
        assertEquals(Order.State.REALIZED, order.getOrderState());
    }
    @Test
    void testOrderRealizedInInvalidHours() {
        Order order = new Order(new DefaultFakeClock());
        order.addItem(new OrderItem());
        order.submit();
        order.getFakeClock().plusHours(VALID_PERIOD_HOURS + 1);
        assertThrows(OrderExpiredException.class, order::confirm);
    }

}
