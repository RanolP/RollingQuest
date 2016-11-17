package me.ranol.rollingquest.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;

public class RealMenuListener implements Listener {
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (MenuAPI.isMenuOpened(p) && e.getSlotType() != SlotType.OUTSIDE) {
			MenuClickEvent click = new MenuClickEvent(MenuAPI.getOpenedMenu(p), e);
			MenuAPI.getOpenedMenu(p).onClick(click);
			Bukkit.getPluginManager().callEvent(click);
		}
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		if (MenuAPI.isMenuOpened(p)) {
			MenuAPI.close(p);
		}
	}
}
