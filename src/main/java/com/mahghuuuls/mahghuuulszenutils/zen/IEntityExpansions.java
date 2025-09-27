package com.mahghuuuls.mahghuuulszenutils.zen;

import java.util.List;

import com.mahghuuuls.mahghuuulszenutils.convert.CtToMc;
import com.mahghuuuls.mahghuuulszenutils.convert.McToCt;
import com.mahghuuuls.mahghuuulszenutils.core.potioneffect.PotionEffectQueryOps;
import com.mahghuuuls.mahghuuulszenutils.core.stacktracker.RefreshRule;
import com.mahghuuuls.mahghuuulszenutils.core.stacktracker.StackTracker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.potions.IPotionEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.PotionEffect;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenExpansion("crafttweaker.entity.IEntity")
@ZenRegister
public class IEntityExpansions {

	@ZenMethod
	public static IPlayer asIPlayer(IEntity iEntity) {
		Entity entity = CraftTweakerMC.getEntity(iEntity);

		if (entity instanceof EntityPlayerMP) {
			return CraftTweakerMC.getIPlayer((EntityPlayerMP) entity);
		}

		return null;
	}

	@ZenMethod
	public static IPotionEffect[] getActivePotionEffects(IEntity ctEntity) {
		EntityLivingBase mcEntity = CtToMc.entity(ctEntity);
		List<PotionEffect> mcPotionEffects = PotionEffectQueryOps.getActivePotionEffects(mcEntity);
		return McToCt.potionEffects(mcPotionEffects);
	}

	@ZenMethod
	public static int getNumberOfActivePotionEffects(IEntity ctEntity) {
		EntityLivingBase mcEntity = CtToMc.entity(ctEntity);
		int potionEffectsCount = PotionEffectQueryOps.getNumberOfActivePotionEffects(mcEntity);
		return potionEffectsCount;
	}

	@ZenMethod
	public static void addStacks(IEntity iEntity, String stackName, long durationTicks, String refreshRule,
			int quantity) {
		EntityLivingBase mcEntity = CtToMc.entity(iEntity);

		if (mcEntity == null) {
			return;
		}

		RefreshRule rule = RefreshRule.fromString(refreshRule);
		StackTracker.addStacks(mcEntity.getUniqueID().toString(), stackName, durationTicks, rule, quantity);
	}

	@ZenMethod
	public static void removeStacks(IEntity iEntity, String stackName, long durationTicks, String refreshRule,
			int quantity) {
		EntityLivingBase mcEntity = CtToMc.entity(iEntity);

		if (mcEntity == null) {
			return;
		}

		RefreshRule rule = RefreshRule.fromString(refreshRule);
		StackTracker.addStacks(mcEntity.getUniqueID().toString(), stackName, durationTicks, rule, -1 * quantity);
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
	public static void setStacks(IEntity iEntity, String stackName, long durationTicks, String refreshRule,
			int quantity) {
		EntityLivingBase mcEntity = CtToMc.entity(iEntity);

		if (mcEntity == null) {
			return;
		}

		int currentStacks = StackTracker.getStackCount(mcEntity.getUniqueID().toString(), stackName);
		RefreshRule rule = RefreshRule.fromString(refreshRule);
		StackTracker.addStacks(mcEntity.getUniqueID().toString(), stackName, durationTicks, rule,
				quantity - currentStacks);
	}
}
