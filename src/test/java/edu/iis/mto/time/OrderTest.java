package edu.iis.mto.time;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;


class OrderTest {

	private Order order;
	private FakeSystemClock fakeSystemClock = new FakeSystemClock();

	@BeforeEach
	void setUp() {
		fakeSystemClock.setTime(LocalDateTime.now());
		order = new Order(fakeSystemClock);
	}

	@Test
	void orderAfterValidPeriodHoursTest() {
		order.submit();
		fakeSystemClock.changeHours(LocalDateTime.now(), order.getValidPeriodHours()+1);
		assertThrows(OrderExpiredException.class, () -> order.confirm());
	}

	@Test
	void orderBeforeValidPeriodHoursTest() {
		order.submit();
		fakeSystemClock.changeHours(LocalDateTime.now(), order.getValidPeriodHours()-1);
		assertDoesNotThrow(() -> order.confirm());
	}

}
