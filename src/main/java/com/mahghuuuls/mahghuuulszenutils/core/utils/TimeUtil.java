package com.mahghuuuls.mahghuuulszenutils.core.utils;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class TimeUtil {

	public static boolean hasTimeExpired(long startTime, long duration, long currentTime) {

		long elapsedTime = getElapsedTime(currentTime, startTime);

		if (elapsedTime < duration) {
			return false;
		}

		return true;

	}

	public static long getElapsedTime(long startTime, long currentTime) {
		return currentTime - startTime;
	}

	public static long getServerTicks() {
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		WorldServer overworld = server.getWorld(0);
		return overworld.getTotalWorldTime();
	}

}
