package com.mahghuuuls.mahghuuulszenutils.core.cooldowntracker;

import java.util.HashMap;

import com.mahghuuuls.mahghuuulszenutils.core.utils.ServerUtil;

public class CooldownTracker {

	private static HashMap<CooldownKey, CooldownData> activeCooldowns = new HashMap<>();

	public static void startCooldown(String playerId, String cooldownId, long durationTicks) {
		long currentTime = ServerUtil.getServerTicks();
		CooldownKey cooldownKey = new CooldownKey(playerId, cooldownId);
		activeCooldowns.put(cooldownKey, new CooldownData(durationTicks, currentTime));
	}

	public static boolean onCooldown(String playerId, String cooldownId) {
		CooldownKey cooldownKey = new CooldownKey(playerId, cooldownId);
		CooldownData cooldownData = activeCooldowns.get(cooldownKey);

		if (cooldownData == null) {
			return false;
		}

		long currentTime = ServerUtil.getServerTicks();
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
