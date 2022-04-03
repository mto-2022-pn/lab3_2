package edu.iis.mto.time;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class OrderTest {
    private static final long VALID_PERIOD_HOURS = 24;
    private Order order;
    @BeforeEach
    void setUp() {
        order = new Order(new DefaultFakeClock());
        order.addItem(new OrderItem());
    }
    @Test
    void testOrderRealizedInValidHours() {
        order.submit();
        assertEquals(Order.State.SUBMITTED, order.getOrderState());
        order.confirm();
        assertEquals(Order.State.CONFIRMED, order.getOrderState());
        order.getFakeClock().plusHours(5);
        order.realize();
        assertEquals(Order.State.REALIZED, order.getOrderState());
    }

    @Test
    void testOrderRealizedInInvalidHoursShouldThrowOrderExpiredException() {
        order.submit();
        order.getFakeClock().plusHours(VALID_PERIOD_HOURS + 1);
        assertThrows(OrderExpiredException.class, order::confirm);
    }

    @Test
    void testOrderConfirmationWithoutSubmitShouldThrowOrderStateException() {
        assertThrows(OrderStateException.class, order::confirm);
    }
}
