package com.mahghuuuls.mahghuuulszenutils.helpers.converters;

import java.util.List;

import crafttweaker.api.entity.IEntity;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.potions.IPotionEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;

public class McToCt {

	public static IEntity entity(EntityLivingBase mcEntity) {

		if (mcEntity == null) {
			return null;
		}

		return CraftTweakerMC.getIEntity(mcEntity);
	}

	public static IPotionEffect[] potionEffects(List<PotionEffect> mcPotionEffects) {
		if (mcPotionEffects == null || mcPotionEffects.isEmpty()) {
			return new IPotionEffect[0];
		}

		return mcPotionEffects.stream().map(McToCt::potionEffect).toArray(IPotionEffect[]::new);
	}

	public static IPotionEffect potionEffect(PotionEffect mcPotionEffect) {
		return mcPotionEffect == null ? null : CraftTweakerMC.getIPotionEffect(mcPotionEffect);
	}
}
