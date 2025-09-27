package com.mahghuuuls.mahghuuulszenutils.helpers;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class TimeHelper {

	public static boolean hasDurationExpired(long startTime, long duration, long currentTime) {

		long elapsedTime = currentTime - startTime;

		if (elapsedTime < duration) {
			return false;
		}

		return true;
	}

	public static long getServerTicks() {
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		WorldServer overworld = server.getWorld(0);
		return overworld.getTotalWorldTime();
	}
}
