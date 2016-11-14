package me.ranol.rollingquest.util;

import org.bukkit.entity.Player;

@FunctionalInterface
public interface PlayerRunnable {
	public void run(Player p);
}
