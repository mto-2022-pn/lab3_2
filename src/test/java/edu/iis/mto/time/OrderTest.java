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
    @Test
    void orderShouldbeInCreatedState(){
        Order order=new Order();
        OrderItem orderItem=new OrderItem();
        order.addItem(orderItem);
        assertEquals(Order.State.CREATED,order.getOrderState());
    }
    @Test
    void orderShouldbeInSubmittedState(){
        Order order=new Order();
        OrderItem orderItem=new OrderItem();
        order.addItem(orderItem);
        order.submit();
        assertEquals(Order.State.SUBMITTED,order.getOrderState());
    }
    @Test
    void orderIsInWrongState(){
        Order order=new Order();
        OrderItem orderItem=new OrderItem();
        order.addItem(orderItem);
        try {
            order.confirm();
            fail("expected OrderStateException");
        }catch(OrderStateException ignored){
        }
    }
    @Test
    void orderShouldBeInConfirmedState(){
        Order order=new Order();
        OrderItem orderItem=new OrderItem();
        order.addItem(orderItem);
        order.submit();
        order.confirm();
        assertEquals(Order.State.CONFIRMED,order.getOrderState());
    }


}
