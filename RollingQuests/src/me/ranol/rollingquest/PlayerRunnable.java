package me.ranol.rollingquest;

import org.bukkit.entity.Player;

@FunctionalInterface
public interface PlayerRunnable {
	public void run(Player p);
}
