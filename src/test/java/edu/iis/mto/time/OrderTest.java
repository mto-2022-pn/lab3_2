package edu.iis.mto.time;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.internal.matchers.Or;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@ExtendWith(MockitoExtension.class)
class OrderTest {

    @Mock
    private Clock clock;
    private Order order;

    @BeforeEach
    void setUp() throws Exception {
        lenient().when(clock.getZone()).thenReturn(ZoneId.systemDefault());
        order = new Order(clock);
    }

    @Test
    void orderConfirmationAfterOrderExpireThrowsOrderExpiredException() {
        Instant orderSubmissionTime = Instant.now();
        Instant orderConformationTime = orderSubmissionTime.plus(27,ChronoUnit.HOURS);

        when(clock.instant()).thenReturn(orderSubmissionTime).thenReturn(orderConformationTime);
        order.submit();
        assertThrows(OrderExpiredException.class,()->order.confirm());
        assertEquals(Order.State.CANCELLED, order.getOrderState());

    }

    @Test
    void orderConfirmationWithinAcceptedTimeChangeOrderState() {
        Instant orderSubmissionTime = Instant.now();
        Instant orderConformationTime = orderSubmissionTime.plus(4,ChronoUnit.HOURS);

        when(clock.instant()).thenReturn(orderSubmissionTime).thenReturn(orderConformationTime);
        order.submit();
        assertEquals(Order.State.SUBMITTED,order.getOrderState());
        order.confirm();
        assertEquals(Order.State.CONFIRMED, order.getOrderState());
        order.realize();
        assertEquals(Order.State.REALIZED, order.getOrderState());

    }

    @Test
    void orderConfirmationWithoutSubmitThrowsOrderStateException() {
        assertThrows(OrderStateException.class,()->order.confirm());
    }

    @Test
    void orderRealizeWithoutConfirmationThrowsOrderStateException(){
        Instant orderSubmissionTime = Instant.now();
        when(clock.instant()).thenReturn(orderSubmissionTime);
        order.submit();
        assertThrows(OrderStateException.class,()->order.realize());

    }
}
