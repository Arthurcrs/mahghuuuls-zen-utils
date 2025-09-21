package com.mahghuuuls.mahghuuulszenutils.core.utils;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ServerUtil {

	public static long getServerTicks() {
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		WorldServer overworld = server.getWorld(0);
		return overworld.getTotalWorldTime();
	}

}
