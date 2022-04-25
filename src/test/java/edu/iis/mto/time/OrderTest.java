package edu.iis.mto.time;

import static org.junit.jupiter.api.Assertions.*;
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
    Instant submissionDate, confirmationDate;

    @BeforeEach
    void setUp() {
        lenient().when(clock.getZone())
                .thenReturn(ZoneId.systemDefault());

        lenient().when(clock.instant())
                .thenReturn(submissionDate)
                .thenReturn(confirmationDate);

        order = new Order(clock);
        submissionDate = Instant.now();
    }

    @Test
    void confirmingOrderWithoutSubmission_shouldThrowOrderStateException() {
        assertThrows(OrderStateException.class, () -> order.confirm());
    }

    @Test
    void elapsedTimeIsNegative_shouldChangeOrderStateToConfirmed() {
        confirmationDate = submissionDate.minus(48, ChronoUnit.HOURS);
        order.submit();
        assertDoesNotThrow(() -> order.confirm());
        assertEquals(Order.State.CONFIRMED, order.getOrderState());
    }

    @Test
    void hoursElapsedEqualToValidPeriodHours_shouldChangeOrderStateToConfirmed() {
        confirmationDate = submissionDate.plus(24, ChronoUnit.HOURS);
        order.submit();
        assertDoesNotThrow(() -> order.confirm());
        assertEquals(Order.State.CONFIRMED, order.getOrderState());
    }

    @Test
    void hoursElapsedGreaterThanValidPeriodHours_shouldChangeOrderStateToCancelledAndThrowOrderExpiredException() {
        confirmationDate = submissionDate.plus(25, ChronoUnit.HOURS);
        order.submit();
        assertThrows(OrderExpiredException.class, () -> order.confirm());
        assertEquals(Order.State.CANCELLED, order.getOrderState());
    }
}
