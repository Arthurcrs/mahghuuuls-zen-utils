package com.mahghuuuls.mahghuuulszenutils.trackers.stacktracker.zen;

import com.mahghuuuls.mahghuuulszenutils.helpers.converters.CtToMc;
import com.mahghuuuls.mahghuuulszenutils.trackers.stacktracker.RefreshRule;
import com.mahghuuuls.mahghuuulszenutils.trackers.stacktracker.StackTracker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import net.minecraft.entity.EntityLivingBase;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenExpansion("crafttweaker.entity.IEntity")
@ZenRegister
public class IEntityExpansions {

	@ZenMethod
	public static void addStacks(IEntity iEntity, String stackName, long quantity, String refreshRule,
			long durationTicks) {
		addStacksCore(iEntity, stackName, quantity, refreshRule, durationTicks);
	}

	@ZenMethod
	public static void addStacks(IEntity iEntity, String stackName, long quantity) {
		addStacksCore(iEntity, stackName, quantity, RefreshRule.PRESERVE.toString(), 0);
	}

	@ZenMethod
	public static void removeStacks(IEntity iEntity, String stackName, long quantity, String refreshRule,
			long durationTicks) {
		addStacksCore(iEntity, stackName, -1 * quantity, refreshRule, durationTicks);
	}

	@ZenMethod
	public static void removeStacks(IEntity iEntity, String stackName, int quantity) {
		addStacksCore(iEntity, stackName, -1 * quantity, RefreshRule.PRESERVE.toString(), 0);
	}

	@ZenMethod
	public static void clearStacks(IEntity iEntity, String stackName) {
		EntityLivingBase mcEntity = CtToMc.entity(iEntity);

		if (mcEntity == null) {
			return;
		}

		StackTracker.clearStacks(mcEntity.getUniqueID().toString(), stackName);
	}

	@ZenMethod
	public static int getStacksCount(IEntity iEntity, String stackName) {
		EntityLivingBase mcEntity = CtToMc.entity(iEntity);

		if (mcEntity == null) {
			return 0;
		}

		return StackTracker.getStackCount(mcEntity.getUniqueID().toString(), stackName);
	}

	@ZenMethod
	public static void setStacks(IEntity iEntity, String stackName, long quantity, String refreshRule,
			long durationTicks) {
		setStacksCore(iEntity, stackName, quantity, refreshRule, durationTicks);
	}

	// CORE

	private static void addStacksCore(IEntity iEntity, String stackName, long quantity, String refreshRule,
			long durationTicks) {
		EntityLivingBase mcEntity = CtToMc.entity(iEntity);

		if (mcEntity == null) {
			return;
		}

		RefreshRule rule = RefreshRule.fromString(refreshRule);
		StackTracker.addStacks(mcEntity.getUniqueID().toString(), stackName, quantity, rule, durationTicks);
	}

	private static void setStacksCore(IEntity iEntity, String stackName, long quantity, String refreshRule,
			long durationTicks) {
		EntityLivingBase mcEntity = CtToMc.entity(iEntity);

		if (mcEntity == null) {
			return;
		}

		int currentStacks = StackTracker.getStackCount(mcEntity.getUniqueID().toString(), stackName);
		RefreshRule rule = RefreshRule.fromString(refreshRule);
		StackTracker.addStacks(mcEntity.getUniqueID().toString(), stackName, quantity - currentStacks, rule,
				durationTicks);
	}

}
