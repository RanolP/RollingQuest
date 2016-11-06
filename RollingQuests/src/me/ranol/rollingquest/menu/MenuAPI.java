package me.ranol.rollingquest.menu;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

public class MenuAPI {
	private static HashMap<UUID, Menu> openedMenu = new HashMap<>();

	public static Menu getOpenedMenu(Player p) {
		return openedMenu.get(p.getUniqueId());
	}

	public static void setOpenedMenu(Player p, Menu m) {
		openedMenu.put(p.getUniqueId(), m);
	}

	public static boolean isMenuOpened(Player p) {
		return openedMenu.containsKey(p.getUniqueId());
	}

	public static void close(Player p) {
		if (MenuAPI.getOpenedMenu(p).close())
			openedMenu.remove(p.getUniqueId());

	}
}
