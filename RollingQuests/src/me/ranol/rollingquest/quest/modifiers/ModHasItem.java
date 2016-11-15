package me.ranol.rollingquest.quest.modifiers;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.ranol.rollingquest.api.RollingModifier;
import me.ranol.rollingquest.item.ItemAPI;
import me.ranol.rollingquest.item.NamedItem;
import me.ranol.rollingquest.util.Util;

public class ModHasItem implements RollingModifier {
	NamedItem item;
	int amt;

	@Override
	public String getString(Player p) {
		return "아이템 " + Util.display(item.getStack()) + "을 " + item.getAmount() + "개 획득하기.";
	}

	@Override
	public void apply(List<String> args) {
		if (args.get(0).matches("[0-9]+")) {
			item = new NamedItem(new ItemStack(Integer.parseInt(args.get(0))));
		} else
			item = ItemAPI.getItem(args.get(0)).get(0);
		try {
			amt = Integer.parseInt(args.get(1));
		} catch (NumberFormatException e) {
			amt = 0;
		}
	}

	@Override
	public boolean activate(Player p) {
		return Util.hasItem(p, item.getStack()) >= amt;
	}

}
