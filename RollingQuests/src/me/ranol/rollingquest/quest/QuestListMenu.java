package me.ranol.rollingquest.quest;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.ranol.rollingquest.management.QuestManager;
import me.ranol.rollingquest.menu.PageMenu;

public class QuestListMenu extends PageMenu<Quest> {

	public QuestListMenu(Player opener) {
		super(opener);
		start = 0;
		end = 27;
	}

	@Override
	public boolean open() {
		if (!super.open())
			return false;
		setInventory(Bukkit.createInventory(null, 9 * 4, "§a◎ §6Quests§f| §e" + getPlayer().getName()));
		super.pageItems.addAll(QuestManager.hasQuests(getPlayer()));
		update();
		calculation();
		getPlayer().openInventory(getInventory());
		return true;
	}

	@Override
	public ItemStack getStack(Quest quest) {
		return quest.toStack(getPlayer());
	}

}
