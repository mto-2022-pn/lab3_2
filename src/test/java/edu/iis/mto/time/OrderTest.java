package edu.iis.mto.time;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


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


}
