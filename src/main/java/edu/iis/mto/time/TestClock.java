package edu.iis.mto.time;

import java.time.LocalDateTime;

public class TestClock implements IClock{

	private LocalDateTime time;
	@Override
	public LocalDateTime now() {
		return time;
	}
	public void setTime(LocalDateTime time){
		this.time = time;
	}
}
