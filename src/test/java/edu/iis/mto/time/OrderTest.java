package edu.iis.mto.time;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;


class OrderTest {

    @BeforeEach
    void setUp() throws Exception {}

    @Test
    void orderConfirmedAndOver24hThrowsException() {

        Order order = new Order(LocalDateTime.now());
        order.addItem(new OrderItem());
        order.submit();
        assertThrows(OrderExpiredException.class,()->order.confirm());
    }

    @Test
    void orderHasIncorrectStatus(){
        Order order = new Order(LocalDateTime.now());
        assertThrows(OrderStateException.class,()->order.confirm());
    }
    @Test
    void orderRealized(){
        Order order = new Order(LocalDateTime.of(2022,3,31,0,0,0));
        order.addItem(new OrderItem());
        order.submit();
        order.confirm();
        order.realize();
        assertEquals(Order.State.REALIZED,order.getOrderState());
    }
}
