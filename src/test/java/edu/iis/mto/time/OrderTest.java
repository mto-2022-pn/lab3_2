package edu.iis.mto.time;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.*;
import java.time.temporal.ChronoUnit;

@ExtendWith(MockitoExtension.class)
class OrderTest {
    @Mock
    Clock clock;
    Order order;
    Instant submissionDate;

    @BeforeEach
    void setUp() throws Exception {
        lenient().when(clock.getZone()).thenReturn(ZoneId.systemDefault());
        order = new Order(clock);
    }

    @Test
    void confirmingOrderWithoutSubmission_shouldThrowOrderStateException() {
        assertThrows(OrderStateException.class, () -> order.confirm());
    }

    @Test
    void elapsedTimeIsNegative_shouldChangeOrderStateToConfirmed() {
        Instant submissionDate = Instant.now();
        Instant confirmationDate = submissionDate.minus(48, ChronoUnit.HOURS);

        when(clock.instant())
                .thenReturn(submissionDate)
                .thenReturn(confirmationDate);
        order.submit();

        assertDoesNotThrow(() -> order.confirm());
        assertEquals(Order.State.CONFIRMED, order.getOrderState());
    }

    @Test
    void hoursElapsedEqualToValidPeriodHours_shouldChangeOrderStateToConfirmed() {
        Instant submissionDate = Instant.now();
        Instant confirmationDate = submissionDate.plus(24, ChronoUnit.HOURS);

        when(clock.instant())
                .thenReturn(submissionDate)
                .thenReturn(confirmationDate);
        order.submit();

        assertDoesNotThrow(() -> order.confirm());
        assertEquals(Order.State.CONFIRMED, order.getOrderState());
    }

    @Test
    void hoursElapsedGreaterThanValidPeriodHours_shouldChangeOrderStateToCancelledAndThrowOrderExpiredException() {
        Instant submissionDate = Instant.now();
        Instant confirmationDate = submissionDate.plus(25, ChronoUnit.HOURS);

        when(clock.instant())
                .thenReturn(submissionDate)
                .thenReturn(confirmationDate);
        order.submit();

        assertThrows(OrderExpiredException.class, () -> order.confirm());
        assertEquals(Order.State.CANCELLED, order.getOrderState());
    }
}
