package com.mahghuuuls.mahghuuulszenutils.zen;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.mahghuuuls.mahghuuulszenutils.convert.CtToMc;
import com.mahghuuuls.mahghuuulszenutils.core.cooldowntracker.CooldownTracker;
import com.mahghuuuls.mahghuuulszenutils.core.entitymarkertracker.EntityMarkerTracker;
import com.mahghuuuls.mahghuuulszenutils.core.stacktracker.RefreshRule;
import com.mahghuuuls.mahghuuulszenutils.core.stacktracker.StackTracker;

import crafttweaker.annotations.ZenRegister;
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
@ZenRegister
public class IPlayerExpansions {

	@ZenMethod
	public static void startCooldown(IPlayer iPlayer, String cooldownId, int duration) {
		CooldownTracker.startCooldown(iPlayer.getUUID(), cooldownId, duration);
	}

	@ZenMethod
	public static boolean onCooldown(IPlayer iPlayer, String cooldownId) {
		return CooldownTracker.onCooldown(iPlayer.getUUID(), cooldownId);
	}

	@ZenMethod
	public static void markEntity(IPlayer iPlayer, String markId, IEntity iEntity, long duration) {
		EntityLivingBase mcEntity = CtToMc.entity(iEntity);
		EntityMarkerTracker.markEntity(iPlayer.getUUID(), markId, mcEntity.getUniqueID().toString(), duration);
	}

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
	public static void addStacks(IPlayer iPlayer, String stackName, long durationTicks, String refreshRule,
			int quantity) {
		RefreshRule rule = RefreshRule.fromString(refreshRule);
		StackTracker.addStacks(iPlayer.getUUID(), stackName, durationTicks, rule, quantity);
	}

	@ZenMethod
	public static void removeStacks(IPlayer iPlayer, String stackName, long durationTicks, String refreshRule,
			int quantity) {
		RefreshRule rule = RefreshRule.fromString(refreshRule);
		StackTracker.addStacks(iPlayer.getUUID(), stackName, durationTicks, rule, -1 * quantity);
	}

	@ZenMethod
	public static void clearStacks(IPlayer iPlayer, String stackName) {
		StackTracker.clearStacks(iPlayer.getUUID(), stackName);
	}

	@ZenMethod
	public static int getStacksCount(IPlayer iPlayer, String stackName) {
		return StackTracker.getStackCount(iPlayer.getUUID(), stackName);
	}

	@ZenMethod
	public static void setStacks(IPlayer iPlayer, String stackName, long durationTicks, String refreshRule,
			int quantity) {
		String playerUuid = iPlayer.getUUID().toString();
		int currentStacks = StackTracker.getStackCount(playerUuid, stackName);
		RefreshRule rule = RefreshRule.fromString(refreshRule);
		StackTracker.addStacks(playerUuid, stackName, durationTicks, rule, quantity - currentStacks);
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
