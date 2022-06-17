package edu.iis.mto.time;

import java.time.LocalDateTime;

public class FakeSystemClock {

	private LocalDateTime time = LocalDateTime.now();

	public LocalDateTime now() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public void changeHours(LocalDateTime time, long hours) {
		this.time = time.plusHours(hours);
	}
}
