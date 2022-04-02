package edu.iis.mto.time;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;


class OrderTest {

    @BeforeEach
    void setUp() throws Exception {}

    @Test
    void orderShouldBeInRealizedState() {
        Order order=new Order();
        OrderItem orderItem=new OrderItem();
        order.addItem(orderItem);
        order.submit();
        order.confirm();
        order.realize();
        assertEquals(Order.State.REALIZED,order.getOrderState());
    }
    @Test
    void orderShouldBeInCancelledState() {
        Order order=new Order(LocalDateTime.of(2022,4,1,0,0,0));
        OrderItem orderItem=new OrderItem();
        order.addItem(orderItem);
        order.submit();
        try {
            order.confirm();
            fail("expected OrderExpiredException");
        }catch(OrderExpiredException ignored){
        }
        assertEquals(Order.State.CANCELLED,order.getOrderState());
    }


}
