package com.mahghuuuls.mahghuuulszenutils.zen;

import java.util.List;

import com.mahghuuuls.mahghuuulszenutils.convert.CtToMc;
import com.mahghuuuls.mahghuuulszenutils.convert.McToCt;
import com.mahghuuuls.mahghuuulszenutils.core.potioneffect.PotionEffectQueryOps;

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

}
