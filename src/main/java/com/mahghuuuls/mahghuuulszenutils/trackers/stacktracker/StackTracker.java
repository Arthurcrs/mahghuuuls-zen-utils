package com.mahghuuuls.mahghuuulszenutils.trackers.stacktracker;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.mahghuuuls.mahghuuulszenutils.helpers.TimeHelper;

public class StackTracker {

	private static final ConcurrentMap<StackTrackerKey, StackTrackerData> activeStacks = new ConcurrentHashMap<>();

	public static void addStacks(String ownerId, String stackName, long stackQuantity, RefreshRule refreshRule,
			long durationTicks) {

		if (ownerId == null || stackName == null) {
			return;
		}

		long currentTime = TimeHelper.getServerTicks();
		StackTrackerKey key = new StackTrackerKey(ownerId, stackName);
		StackTrackerData data = activeStacks.get(key);

		if (data != null && data.isExpired(currentTime)) {
			activeStacks.remove(key);
			data = null;
		}

		if (data == null && stackQuantity <= 0) {
			return;
		}

		if (data == null) {

			StackTrackerData newData = new StackTrackerData((int) stackQuantity, currentTime, durationTicks);

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
			// TODO Add warning log
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

		long currentTime = TimeHelper.getServerTicks();
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
