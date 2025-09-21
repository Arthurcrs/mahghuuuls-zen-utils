package com.mahghuuuls.mahghuuulszenutils.core.cooldowntracker;

public class CooldownData {
	public long durationTicks;
	public long startTime;

	CooldownData(long durationTicks, long startTime) {
		this.durationTicks = durationTicks;
		this.startTime = startTime;
	}
}
