package com.mahghuuuls.mahghuuulszenutils.core.stacktracker;

import java.util.HashMap;
import java.util.Map;

import com.mahghuuuls.mahghuuulszenutils.core.utils.TimeUtil;

public class StackTracker {

	private static final Map<StackTrackerKey, StackTrackerData> activeStacks = new HashMap<>();

	public static void addStacks(String ownerId, String stackName, long durationTicks, RefreshRule refreshRule,
			int stackQuantity) {

		if (ownerId == null || stackName == null) {
			return;
		}

		long currentTime = TimeUtil.getServerTicks();
		StackTrackerKey key = new StackTrackerKey(ownerId, stackName);
		StackTrackerData data = activeStacks.get(key);

		if (data != null && data.isExpired(currentTime)) {
			activeStacks.remove(key);
			data = null;
		}

		if (data == null) {
			StackTrackerData newData = new StackTrackerData(stackQuantity, currentTime, durationTicks);
			if (!newData.isExpired(currentTime)) {
				activeStacks.put(key, newData);
			}
			return;
		}

		if (stackQuantity != 0) {
			data.stackCount += stackQuantity;
		}

		if (data.stackCount <= 0) {
			activeStacks.remove(key);
			return;
		}

		switch (refreshRule) {
		case RESET:
			data.startTime = currentTime;
			data.durationTicks = durationTicks;
			break;
		case ADD:
			long remainingDuration = data.getRemainingDuration(currentTime);
			data.startTime = currentTime;
			data.durationTicks = remainingDuration + durationTicks;
			break;
		case PRESERVE:
			break;
		default:
			break;
		}

		if (data.isExpired(currentTime)) {
			activeStacks.remove(key);
		}

	}

	public static void clearStacks(String ownerId, String stackName) {

		if (ownerId == null || stackName == null) {
			return;
		}

		StackTrackerKey key = new StackTrackerKey(ownerId, stackName);
		activeStacks.remove(key);
	}

	public static int getStackCount(String ownerId, String stackName) {

		if (ownerId == null || stackName == null) {
			return 0;
		}

		long currentTime = TimeUtil.getServerTicks();
		StackTrackerKey key = new StackTrackerKey(ownerId, stackName);
		StackTrackerData data = activeStacks.get(key);

		if (data == null) {
			return 0;
		}

		if (data.isExpired(currentTime)) {
			activeStacks.remove(key);
			return 0;
		}

		return data.stackCount;
	}
}
