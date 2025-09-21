package com.mahghuuuls.mahghuuulszenutils.core.entitymarkertracker;

import java.util.Objects;

public class EntityMarkerKey {

	public String playerUuid;
	public String markId;
	public String entityUuid;

	EntityMarkerKey(String playerId, String markId, String entityId) {
		this.playerUuid = playerId;
		this.markId = markId;
		this.entityUuid = entityId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof EntityMarkerKey)) {
			return false;
		}

		EntityMarkerKey that = (EntityMarkerKey) obj;
		return Objects.equals(playerUuid, that.playerUuid) && Objects.equals(markId, that.markId)
				&& Objects.equals(entityUuid, that.entityUuid);
	}

	@Override
	public int hashCode() {
		return Objects.hash(playerUuid, entityUuid, markId);
	}

}
