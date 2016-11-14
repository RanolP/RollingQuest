package me.ranol.rollingquest.menu;

import java.util.List;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MenuClickEvent extends InventoryClickEvent {
	private static final HandlerList handlers = new HandlerList();
	private Menu menu;
	private InventoryClickEvent e;

	public MenuClickEvent(Menu menu, InventoryClickEvent e) {
		super(e.getView(), e.getSlotType(), e.getRawSlot(), e.getClick(), e.getAction());
		this.menu = menu;
		this.e = e;
	}

	public InventoryClickEvent getEvent() {
		return e;
	}

	@Override
	public void setCancelled(boolean toCancel) {
		getEvent().setCancelled(toCancel);
	}

	@Override
	public void setCurrentItem(ItemStack stack) {
		getEvent().setCurrentItem(stack);
	}

	@Override
	@Deprecated
	public void setCursor(ItemStack stack) {
		getEvent().setCursor(stack);
	}

	@Override
	public void setResult(Result newResult) {
		getEvent().setResult(newResult);
	}

	@Override
	public Inventory getClickedInventory() {
		return getEvent().getClickedInventory();
	}

	@Override
	public ItemStack getCurrentItem() {
		return getEvent().getCurrentItem();
	}

	@Override
	public ItemStack getCursor() {
		return getEvent().getCursor();
	}

	@Override
	public int getHotbarButton() {
		return getEvent().getHotbarButton();
	}

	@Override
	public Inventory getInventory() {
		return getEvent().getInventory();
	}

	@Override
	public int getRawSlot() {
		return getEvent().getRawSlot();
	}

	@Override
	public int getSlot() {
		return getEvent().getSlot();
	}

	@Override
	public List<HumanEntity> getViewers() {
		return getEvent().getViewers();
	}

	@Override
	public boolean isCancelled() {
		return getEvent().isCancelled();
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
