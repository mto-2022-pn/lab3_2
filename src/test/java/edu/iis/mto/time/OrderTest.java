package edu.iis.mto.time;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class OrderTest {

    @BeforeEach
    void setUp(){}

    @Test
    void testOrderConfirmAfterExpireDateShouldThrowOrderExpiredException() {
        Order order = new Order();
        order.submit();
        assertThrows(OrderExpiredException.class, order::confirm);
    }

    @Test
    void testOrderConfirmBeforeExpireDateConfirm() {
        Order order = new Order();
        order.submit();
        assertDoesNotThrow(order::confirm);
        assertEquals(Order.State.CONFIRMED, order.getOrderState());
    }
}
