package me.ranol.rollingquest.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import me.ranol.rollingquest.quest.QuestListMenu;
import me.ranol.rollingquest.util.CompletionUtils;

public class CmdRollingQuestUser implements TabExecutor {

	@Override
	public List<String> onTabComplete(CommandSender s, Command c, String l, String[] a) {
		List<String> completions = new ArrayList<>();
		if (a.length == 1) {
			completions.addAll(Arrays.asList("help", "list"));
			CompletionUtils.complete(completions, a[0]);
		}
		return completions;
	}

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
		if (a.length == 0 || a[0].equalsIgnoreCase("help")) {
			help(s, l);
			return true;
		} else if (a[0].equalsIgnoreCase("list")) {
			if (s instanceof Player)
				list(s);
			return true;
		}
		return true;
	}

	private void list(CommandSender s) {
		QuestListMenu menu = new QuestListMenu((Player) s);
		menu.open();
	}

	private void help(CommandSender s, String l) {
		s.sendMessage("§6* §e/" + l + " help §f- §a도움말을 출력합니다.");
		s.sendMessage("§6* §e/" + l + " list §f- §a받은 퀘스트 목록을 봅니다.");
	}

}
