package me.ranol.rollingquest.quest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import me.ranol.rollingquest.menu.Menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class QuestMenu extends Menu {
	Quest quest;
	List<QuestDialog> availableDialogs = new ArrayList<QuestDialog>();
	List<QuestDialog> allDialogs = new ArrayList<QuestDialog>();

	public QuestMenu(Quest quest, Player player) {
		super(player);
		this.quest = quest;
	}

	public Quest getQuest() {
		return quest;
	}

	public void enableDialog(QuestDialog dialog) {
		if (!dialog.isVisible())
			return;
		List<QuestDialog> collected = availableDialogs.stream()
				.filter(d -> d.getSlot() == dialog.getSlot())
				.collect(Collectors.toList());
		if (collected.size() != 0) {
			collected.forEach(this::disableDialog);
		}
		availableDialogs.add(dialog);
		dialog.visible(this);
	}

	public void disableDialog(QuestDialog dialog) {
		dialog.setVisible(false);
		getInventory().setItem(dialog.getSlot(), new ItemStack(Material.AIR));
		availableDialogs.remove(dialog);
	}

	@Override
	public void onClick(InventoryClickEvent e) {
		e.setCancelled(true);
		List<QuestDialog> collected = availableDialogs.stream()
				.filter(d -> d.getSlot() == e.getSlot() && d.isVisible())
				.collect(Collectors.toList());
		if (collected.size() <= 0)
			return;
		QuestDialog dialog = collected.get(0);
		if (dialog.isPlaying())
			dialog.skip();
		else {
			dialog.activate(this);
			getPlayer().sendMessage(dialog.toString());
		}
	}

	@Override
	public boolean open() {
		if (!super.open())
			return false;
		String title = "§1Quests§f| §2" + quest.getGiver().getName();
		setInventory(Bukkit.createInventory(null, 9 * 4,
				title.length() >= 30 ? title.substring(0, 30) + ".." : title));
		allDialogs.addAll(quest.getDialogs());
		allDialogs.forEach(this::enableDialog);
		getPlayer().openInventory(getInventory());
		return true;
	}
}
