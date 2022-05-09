package edu.iis.mto.time;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;

@ExtendWith(MockitoExtension.class)
class OrderTest {
    @Mock
    private Clock clock;
    private Order order;
    private Instant orderSubmitTime;
    private Instant orderConfirmTime;

    @BeforeEach
    void setUp() {
        when(clock.getZone())
                .thenReturn(ZoneId.systemDefault());

        order = new Order(clock);
    }

    @Test
    void testOrderConfirmAfterExpireDateShouldThrowOrderExpiredException() {
        orderSubmitTime = Instant.now();
        orderConfirmTime = orderSubmitTime.plus(25, ChronoUnit.HOURS);

        when(clock.instant())
                .thenReturn(orderSubmitTime)
                .thenReturn(orderConfirmTime);

        order.submit();
        assertThrows(OrderExpiredException.class, order::confirm);
        assertEquals(Order.State.CANCELLED, order.getOrderState());
    }

    @Test
    void testOrderConfirmBeforeExpireDateConfirm() {
        orderSubmitTime = Instant.now();
        orderConfirmTime = orderSubmitTime.plus(24, ChronoUnit.HOURS);

        when(clock.instant())
                .thenReturn(orderSubmitTime)
                .thenReturn(orderConfirmTime);

        order.submit();
        assertDoesNotThrow(order::confirm);
        assertEquals(Order.State.CONFIRMED, order.getOrderState());
    }
}
