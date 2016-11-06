package me.ranol.rollingquest.quest.commands;

import java.util.List;

import org.bukkit.Bukkit;

import me.ranol.rollingquest.quest.QuestMenu;
import me.ranol.rollingquest.util.StringParser;

public class CmdIssueCommandOp implements DialogCommand {
	String cmd;

	@Override
	public void apply(List<String> args) {
		cmd = StringParser.parse(args);
	}

	@Override
	public void activate(QuestMenu menu) {
		boolean op = menu.getPlayer().isOp();
		menu.getPlayer().setOp(true);
		Bukkit.dispatchCommand(menu.getPlayer(), DialogCommand.parse(cmd, menu));
		menu.getPlayer().setOp(op);
	}

}
