package me.ranol.rollingquest.quest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import me.ranol.rollingquest.menu.Menu;
import me.ranol.rollingquest.menu.MenuCloseEvent;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class QuestMenu extends Menu {
	Quest quest;
	List<QuestDialog> allDialogs = new ArrayList<QuestDialog>();

	public QuestMenu(Quest quest, Player player) {
		super(player);
		this.quest = quest;
	}

	public Quest getQuest() {
		return quest;
	}

	public void enableDialog(QuestDialog dialog) {
		dialog.setVisible(true);
		dialog.visible(this);
	}

	public void disableDialog(QuestDialog dialog) {
		dialog.setVisible(false);
		getInventory().setItem(dialog.getSlot(), new ItemStack(Material.AIR));
	}

	@Override
	public void onClick(InventoryClickEvent e) {
		getPlayer().sendMessage(allDialogs.toString());
		e.setCancelled(true);
		List<QuestDialog> collected = allDialogs.stream()
				.filter(d -> d.getSlot() == e.getSlot() && d.isVisible())
				.collect(Collectors.toList());
		if (collected.size() <= 0)
			return;
		QuestDialog dialog = collected.get(0);
		if (dialog.isPlaying())
			dialog.skip();
		else {
			dialog.activate(this);
		}
	}

	@Override
	public boolean open() {
		if (!super.open())
			return false;
		String title = "§1Quests§f| §2" + quest.getGiver().getName();
		setInventory(Bukkit.createInventory(null, 9 * 4,
				title.length() >= 30 ? title.substring(0, 30) + ".." : title));
		quest.getDialogs().stream().map(d -> d.clone())
				.forEach(allDialogs::add);
		allDialogs.stream().filter(d -> d.isVisible())
				.forEach(this::enableDialog);
		getPlayer().openInventory(getInventory());
		return true;
	}

	@Override
	public void onClose(MenuCloseEvent e) {
		allDialogs.forEach(d -> d.cancelTask());
	}

	public QuestDialog getDialog(String name) {
		QuestDialog result = null;
		List<QuestDialog> filtered = allDialogs.stream()
				.filter(d -> d.getName().equals(name))
				.collect(Collectors.toList());
		if (filtered.size() > 0)
			result = filtered.get(0);
		return result;
	}

	public List<QuestDialog> getDialogs(int slot) {
		return allDialogs.stream().filter(d -> d.getSlot() == slot)
				.collect(Collectors.toList());
	}

	public void disableDialogs(List<QuestDialog> dialogs) {
		dialogs.forEach(this::disableDialog);
	}
}
