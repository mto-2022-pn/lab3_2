package edu.iis.mto.time;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@ExtendWith(MockitoExtension.class)
class OrderTest {

    @Mock
    private Clock clock;
    private Order order;
    private OrderItem orderItem;
    private ZoneId zoneId;
    private LocalDateTime time;

    @BeforeEach
    void setUp() throws Exception {
        lenient().when(clock.getZone()).thenReturn(ZoneId.systemDefault());
        order = new Order(clock);
        orderItem=new OrderItem();
        order.addItem(orderItem);
        zoneId = ZoneId.systemDefault();
        time = LocalDateTime.now();
    }

    @Test
    void orderShouldBeInRealizedState() {
        when(clock.instant()).thenReturn(time.atZone(zoneId).toInstant());
        order.submit();
        order.confirm();
        order.realize();
        assertEquals(Order.State.REALIZED,order.getOrderState());
    }
    @Test
    void orderShouldBeInCancelledState() {
        when(clock.instant()).thenReturn(time.atZone(zoneId).toInstant());
        order.submit();
        time = LocalDateTime.now().plusDays(5);
        when(clock.instant()).thenReturn(time.atZone(zoneId).toInstant());
        assertThrows(OrderExpiredException.class,()->order.confirm());
        assertEquals(Order.State.CANCELLED,order.getOrderState());
    }
    @Test
    void orderShouldbeInCreatedState(){
        assertEquals(Order.State.CREATED,order.getOrderState());
    }
    @Test
    void orderShouldbeInSubmittedState(){
        when(clock.instant()).thenReturn(time.atZone(zoneId).toInstant());
        order.submit();
        assertEquals(Order.State.SUBMITTED,order.getOrderState());
    }
    @Test
    void orderIsInWrongState(){
        assertThrows(OrderStateException.class,()->order.confirm());
    }
    @Test
    void orderShouldBeInConfirmedState(){
        when(clock.instant()).thenReturn(time.atZone(zoneId).toInstant());
        order.submit();
        order.confirm();
        assertEquals(Order.State.CONFIRMED,order.getOrderState());
    }

}
