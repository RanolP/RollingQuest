package me.ranol.rollingquest.quest.commands;

import java.util.List;

import me.ranol.rollingquest.quest.QuestMenu;
import me.ranol.rollingquest.util.StringParser;

public class CmdChatMessage implements QuestCommand {
	String msg;

	@Override
	public void apply(List<String> args) {
		msg = StringParser.parse(args);
	}

	@Override
	public void activate(QuestMenu menu) {
		menu.getPlayer().sendMessage(msg);
	}

}
