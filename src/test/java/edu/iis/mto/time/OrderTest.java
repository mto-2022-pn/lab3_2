package edu.iis.mto.time;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Or;

import java.time.LocalDateTime;


class OrderTest {
    private TestClock clock = new TestClock();
    private Order order;
    @BeforeEach
    void setUp() throws Exception {
        clock.setTime(LocalDateTime.now());
        order = new Order(clock);
    }

    @Test
    void orderConfirmedAndOver24hThrowsException() {
        order.addItem(new OrderItem());
        order.submit();
        clock.setTime(LocalDateTime.now().plusHours(25));
        assertThrows(OrderExpiredException.class,()->order.confirm());
    }

    @Test
    void orderHasIncorrectStatus(){
        assertThrows(OrderStateException.class,()->order.confirm());
    }
    @Test
    void orderRealized(){
        order.addItem(new OrderItem());
        order.submit();
        order.confirm();
        order.realize();
        assertEquals(Order.State.REALIZED,order.getOrderState());
    }
}
