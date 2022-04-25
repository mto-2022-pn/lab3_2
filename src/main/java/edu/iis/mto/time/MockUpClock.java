package edu.iis.mto.time;

import java.time.LocalDateTime;

public class MockUpClock extends Clock {
	private LocalDateTime time = LocalDateTime.now();

	@Override
	public LocalDateTime now() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}
}
