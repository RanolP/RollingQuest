package me.ranol.rollingquest.quest.commands;

import java.util.List;

import me.ranol.rollingquest.api.RollingCommand;
import me.ranol.rollingquest.quest.QuestMenu;

public class CmdCloseInv implements RollingCommand {

	@Override
	public void apply(List<String> args) {
	}

	@Override
	public void activate(QuestMenu menu) {
		menu.getPlayer().closeInventory();
	}

}
