package me.ranol.rollingquest.menu;

import org.bukkit.event.Cancellable;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MenuClickEvent extends InventoryClickEvent implements Cancellable {
	private Menu menu;
	private boolean cancel;
	private InventoryClickEvent e;

	public MenuClickEvent(Menu menu, InventoryClickEvent e) {
		super(e.getView(), e.getSlotType(), e.getRawSlot(), e.getClick(), e.getAction());
		this.menu = menu;
		this.e = e;
	}

	public InventoryClickEvent getEvent() {
		return e;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	@Override
	public boolean isCancelled() {
		return cancel;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}

}
