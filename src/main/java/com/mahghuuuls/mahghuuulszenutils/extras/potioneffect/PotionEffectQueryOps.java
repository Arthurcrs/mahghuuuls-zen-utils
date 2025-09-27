package com.mahghuuuls.mahghuuulszenutils.extras.potioneffect;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class PotionEffectQueryOps {

	public static boolean hasPotionEffect(EntityLivingBase entity, String potionId) {
		Potion potionEffect = Potion.getPotionFromResourceLocation(potionId);

		if (potionEffect == null) {
			return false; // TODO: add error log
		}

		return entity.isPotionActive(potionEffect);
	}

	public static List<PotionEffect> getActivePotionEffects(EntityLivingBase entity) {
		return new ArrayList<>(entity.getActivePotionEffects());
	}
}
