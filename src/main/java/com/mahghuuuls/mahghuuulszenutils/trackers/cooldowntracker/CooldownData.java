package com.mahghuuuls.mahghuuulszenutils.trackers.cooldowntracker;

public class CooldownData {
	public long durationTicks;
	public long startTime;

	CooldownData(long durationTicks, long startTime) {
		this.durationTicks = durationTicks;
		this.startTime = startTime;
	}
}
