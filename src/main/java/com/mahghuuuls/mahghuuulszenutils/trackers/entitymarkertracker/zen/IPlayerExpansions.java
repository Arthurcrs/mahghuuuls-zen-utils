package com.mahghuuuls.mahghuuulszenutils.trackers.entitymarkertracker.zen;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.mahghuuuls.mahghuuulszenutils.helpers.converters.CtToMc;
import com.mahghuuuls.mahghuuulszenutils.trackers.entitymarkertracker.EntityMarkerTracker;

import crafttweaker.api.entity.IEntity;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenExpansion("crafttweaker.player.IPlayer")
public class IPlayerExpansions {

	@ZenMethod
	public static IEntity[] getMarkedEntities(IPlayer iPlayer, String markId) {
		List<String> entityUuidWithTheMark = EntityMarkerTracker.getMarkedEntityUuids(iPlayer.getUUID(), markId);
		List<IEntity> entitiesWithTheMark = new ArrayList<>(entityUuidWithTheMark.size());

		for (String currentUuid : entityUuidWithTheMark) {
			Entity entity = findEntity(UUID.fromString(currentUuid));

			if (entity != null && !entity.isDead) {
				entitiesWithTheMark.add(CraftTweakerMC.getIEntity(entity));
			}

		}

		return entitiesWithTheMark.toArray(new IEntity[0]);
	}

	@ZenMethod
	public static void markEntity(IPlayer iPlayer, String markId, IEntity iEntity, long duration) {
		EntityLivingBase mcEntity = CtToMc.entity(iEntity);

		if (mcEntity == null) {
			return;
		}

		EntityMarkerTracker.markEntity(iPlayer.getUUID(), markId, mcEntity.getUniqueID().toString(), duration);
	}

	private static Entity findEntity(UUID uuid) {

		for (WorldServer worldServer : DimensionManager.getWorlds()) {
			Entity entity = worldServer.getEntityFromUuid(uuid);

			if (entity != null) {
				return entity;
			}

		}

		return null;
	}
}
