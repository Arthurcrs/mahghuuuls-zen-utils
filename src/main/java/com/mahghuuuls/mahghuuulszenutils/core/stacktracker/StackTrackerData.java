package com.mahghuuuls.mahghuuulszenutils.core.stacktracker;

class StackTrackerData {
	int stackCount;
	long startTime;
	long durationTicks;

	StackTrackerData(int stackCount, long startTime, long durationTicks) {
		this.stackCount = stackCount;
		this.startTime = startTime;
		this.durationTicks = durationTicks;
	}

	boolean isExpired(long currentTime) {

		if (durationTicks <= 0) {
			return true;
		}

		long elapsedTime = currentTime - startTime;

		if (elapsedTime < 0) {
			return false;
		}

		return elapsedTime >= durationTicks;
	}

	long getRemainingDuration(long currentTime) {

		if (durationTicks <= 0) {
			return 0;
		}

		long elapsedTime = currentTime - startTime;

		if (elapsedTime <= 0) {
			return durationTicks;
		}

		long remaining = durationTicks - elapsedTime;

		if (remaining < 0) {
			return 0;
		}

		return remaining;
	}
}
