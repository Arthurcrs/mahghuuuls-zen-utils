package com.mahghuuuls.mahghuuulszenutils.trackers.cooldowntracker;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.mahghuuuls.mahghuuulszenutils.helpers.TimeHelper;

public class CooldownTracker {

	private static ConcurrentMap<CooldownKey, CooldownData> activeCooldowns = new ConcurrentHashMap<>();

	public static void startCooldown(String playerId, String cooldownId, long durationTicks) {
		long currentTime = TimeHelper.getServerTicks();
		CooldownKey cooldownKey = new CooldownKey(playerId, cooldownId);
		activeCooldowns.put(cooldownKey, new CooldownData(durationTicks, currentTime));
	}

	public static boolean onCooldown(String playerId, String cooldownId) {
		CooldownKey cooldownKey = new CooldownKey(playerId, cooldownId);
		CooldownData cooldownData = activeCooldowns.get(cooldownKey);

		if (cooldownData == null) {
			return false;
		}

		long currentTime = TimeHelper.getServerTicks();
		long cooldownStartTime = cooldownData.startTime;
		long cooldownDuration = cooldownData.durationTicks;
		long elapsedTime = currentTime - cooldownStartTime;

		if (elapsedTime < cooldownDuration) {
			return true;
		}

		activeCooldowns.remove(cooldownKey);
		return false;
	}
}
