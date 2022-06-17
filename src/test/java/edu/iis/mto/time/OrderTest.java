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
	void orderRealizedInValidTime() {
		order.submit();
		fakeSystemClock.changeHours(LocalDateTime.now(), order.getValidPeriodHours());
		assertDoesNotThrow(() -> order.confirm());
	}

}
