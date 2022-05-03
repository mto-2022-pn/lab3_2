package edu.iis.mto.time;

import java.time.LocalDateTime;

public class Clock implements IClock{
	@Override
	public LocalDateTime now() {
		return LocalDateTime.now();
	}
}
