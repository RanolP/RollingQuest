package me.ranol.rollingquest.quest.commands;

import java.util.List;

import org.bukkit.Bukkit;

import me.ranol.rollingquest.quest.QuestMenu;
import me.ranol.rollingquest.util.StringParser;

public class CmdIssueCommand implements QuestCommand {
	String cmd;

	@Override
	public void apply(List<String> args) {
		cmd = StringParser.parse(args);
	}

	@Override
	public void activate(QuestMenu menu) {
		Bukkit.dispatchCommand(menu.getPlayer(), cmd);
	}

}
