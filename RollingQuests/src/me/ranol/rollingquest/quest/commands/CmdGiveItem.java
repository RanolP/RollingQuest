package me.ranol.rollingquest.quest.commands;

import java.util.List;

import me.ranol.rollingquest.api.RollingCommand;
import me.ranol.rollingquest.item.ItemAPI;
import me.ranol.rollingquest.item.NamedItem;
import me.ranol.rollingquest.quest.QuestMenu;
import me.ranol.rollingquest.util.Util;

public class CmdGiveItem implements RollingCommand {
	NamedItem stack;

	@Override
	public void apply(List<String> args) {
		stack = ItemAPI.getItem(args.get(0)).get(0);
	}

	@Override
	public void activate(QuestMenu menu) {
		Util.giveItem(menu.getPlayer(), stack.getStack());
	}

}
