package me.ranol.rollingquest.menu;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.ranol.rollingquest.RollingQuest;

public class Menu {
	UUID uuid;
	Inventory inventory;

	public Menu(Player opener) {
		this.uuid = opener.getUniqueId();
	}

	public Player getPlayer() {
		return Bukkit.getPlayer(uuid);
	}

	public boolean open() {
		MenuOpenEvent open = new MenuOpenEvent(getPlayer(), this);
		Bukkit.getPluginManager().callEvent(open);
		if (!open.isCancelled())
			MenuAPI.setOpenedMenu(getPlayer(), this);
		return !open.isCancelled();
	}

	public final boolean close() {
		MenuCloseEvent close = new MenuCloseEvent(getPlayer(), this);
		Bukkit.getPluginManager().callEvent(close);
		if (close.isCancelled()) {
			RollingQuest.addDelayedTask(
					() -> getPlayer().openInventory(getInventory()), 1);
		}
		onClose(close);
		return !close.isCancelled();
	}

	public void setInventory(Inventory inv) {
		this.inventory = inv;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void onClick(MenuClickEvent e) {

	}

	public void onClose(MenuCloseEvent e) {

	}
}
