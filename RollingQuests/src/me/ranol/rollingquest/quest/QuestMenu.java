package me.ranol.rollingquest.quest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.ranol.rollingquest.api.DialogSet;
import me.ranol.rollingquest.api.MessageDialog;
import me.ranol.rollingquest.menu.Menu;
import me.ranol.rollingquest.menu.MenuClickEvent;
import me.ranol.rollingquest.menu.MenuCloseEvent;
import me.ranol.rollingquest.util.PlaceHolders;

public class QuestMenu extends Menu {
	DialogSet quest;
	List<MessageDialog> allDialogs = new ArrayList<MessageDialog>();

	public QuestMenu(DialogSet quest, Player player) {
		super(player);
		this.quest = quest;
	}

	public DialogSet getQuest() {
		return quest;
	}

	public void enableDialog(MessageDialog dialog) {
		dialog.setVisible(true);
		dialog.visible(this);
	}

	public void disableDialog(MessageDialog dialog) {
		dialog.setVisible(false);
		getInventory().setItem(dialog.getSlot(), new ItemStack(Material.AIR));
	}

	@Override
	public void onClick(MenuClickEvent e) {
		e.setCancelled(true);
		List<MessageDialog> collected = allDialogs.stream().filter(d -> d.getSlot() == e.getSlot() && d.isVisible())
				.collect(Collectors.toList());
		if (collected.isEmpty())
			return;
		MessageDialog dialog = collected.get(0);
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
		String title = "§1Quests§f| §2" + quest.getGiver().getVisibleName();
		setInventory(Bukkit.createInventory(null, 9 * 4, title.length() >= 30 ? title.substring(0, 30) + ".." : title));
		quest.getDialogs().stream().map(d -> d.clone()).forEach(allDialogs::add);
		allDialogs.stream().filter(d -> d.isVisible()).forEach(this::enableDialog);
		getPlayer().openInventory(getInventory());
		return true;
	}

	@Override
	public void onClose(MenuCloseEvent e) {
		allDialogs.forEach(d -> d.cancelTask());
	}

	public MessageDialog getDialog(String name) {
		MessageDialog result = null;
		List<MessageDialog> filtered = allDialogs.stream().filter(d -> d.getName().equals(name))
				.collect(Collectors.toList());
		if (filtered.size() > 0)
			result = filtered.get(0);
		return result;
	}

	public List<MessageDialog> getDialogs(int slot) {
		return allDialogs.stream().filter(d -> d.getSlot() == slot).collect(Collectors.toList());
	}

	public void disableDialogs(List<MessageDialog> dialogs) {
		dialogs.forEach(this::disableDialog);
	}

	public String parse(String msg) {
		return PlaceHolders.replaceAll(
				msg.replace("<player>", getPlayer().getName()).replace("<npc>", quest.getGiver().getVisibleName()));
	}
}
