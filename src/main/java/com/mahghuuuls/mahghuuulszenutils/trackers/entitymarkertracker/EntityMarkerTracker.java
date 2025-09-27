package com.mahghuuuls.mahghuuulszenutils.trackers.entitymarkertracker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.mahghuuuls.mahghuuulszenutils.helpers.TimeHelper;

public class EntityMarkerTracker {

	private static ConcurrentMap<EntityMarkerKey, EntityMarkerData> markedEntities = new ConcurrentHashMap<>();

	public static void markEntity(String playerUuid, String markId, String entityUuid, long markDuration) {
		EntityMarkerKey key = new EntityMarkerKey(playerUuid, markId, entityUuid);
		long startTime = TimeHelper.getServerTicks();
		EntityMarkerData data = new EntityMarkerData(markDuration, startTime);
		markedEntities.put(key, data);
	}

	public static void clearMark(String playerUuid, String markId, String entityUuid) {
		EntityMarkerKey key = new EntityMarkerKey(playerUuid, markId, entityUuid);
		markedEntities.remove(key);
	}

	public static boolean isMarked(String playerUuid, String markId, String entityUuid) {
		EntityMarkerKey key = new EntityMarkerKey(playerUuid, markId, entityUuid);
		EntityMarkerData data = markedEntities.get(key);

		if (data == null) {
			return false;
		}

		long currentTime = TimeHelper.getServerTicks();

		if (isMarkActive(data, currentTime)) {
			return true;
		}

		markedEntities.remove(key);
		return false;
	}

	public static List<String> getMarkedEntityUuids(String playerUuid, String markId) {
		List<String> result = new ArrayList<>();
		long currentTime = TimeHelper.getServerTicks();

		for (Iterator<Map.Entry<EntityMarkerKey, EntityMarkerData>> iterator = markedEntities.entrySet()
				.iterator(); iterator.hasNext();) {
			Map.Entry<EntityMarkerKey, EntityMarkerData> entry = iterator.next();
			EntityMarkerKey key = entry.getKey();
			EntityMarkerData data = entry.getValue();

			if (!isMarkActive(data, currentTime)) {
				iterator.remove();
				continue;
			}

			if (key.playerUuid.equals(playerUuid) && key.markId.equals(markId)) {
				result.add(key.entityUuid);
			}
		}

		return result;
	}

	private static boolean isMarkActive(EntityMarkerData data, long currentTime) {

		long markStartTime = data.startTime;
		long markDuration = data.durationTicks;

		return TimeHelper.hasDurationExpired(markStartTime, markDuration, currentTime);
	}
}
