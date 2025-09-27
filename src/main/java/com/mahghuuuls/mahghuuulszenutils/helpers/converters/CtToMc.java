package com.mahghuuuls.mahghuuulszenutils.helpers.converters;

import crafttweaker.api.entity.IEntity;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class CtToMc {

	public static EntityLivingBase entity(IEntity ctEntity) {

		if (ctEntity == null) {
			return null;
		}

		if (ctEntity instanceof IEntityLivingBase) {
			return CraftTweakerMC.getEntityLivingBase((IEntityLivingBase) ctEntity);
		}

		Entity mcEntity = CraftTweakerMC.getEntity(ctEntity);
		return (mcEntity instanceof EntityLivingBase) ? (EntityLivingBase) mcEntity : null;
	}

}
