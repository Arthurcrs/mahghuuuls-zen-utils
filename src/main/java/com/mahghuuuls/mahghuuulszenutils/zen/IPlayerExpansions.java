package com.mahghuuuls.mahghuuulszenutils.zen;

import com.mahghuuuls.mahghuuulszenutils.cooldowntracker.CooldownTracker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.player.IPlayer;
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

}
