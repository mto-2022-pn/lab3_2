package edu.iis.mto.time;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

@ExtendWith(MockitoExtension.class)
class OrderTest {
    @Mock
    Clock clock;
    Order order;

    @BeforeEach
    void setUp() throws Exception {
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());
        order = new Order(clock);
    }

    @Test
    void hoursElapsedGreaterThanValidPeriodHours_shouldThrowOrderExpiredException() {
        Instant submissionDate = Instant.now();
        Instant confirmationDate = submissionDate.plus(25, ChronoUnit.HOURS);

        when(clock.instant())
                .thenReturn(submissionDate)
                .thenReturn(confirmationDate);
        order.submit();
        assertThrows(OrderExpiredException.class, () -> order.confirm());
    }
}
