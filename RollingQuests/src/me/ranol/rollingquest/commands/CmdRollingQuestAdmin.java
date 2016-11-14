package me.ranol.rollingquest.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import me.ranol.rollingquest.management.DialogManager;
import me.ranol.rollingquest.management.NpcManager;
import me.ranol.rollingquest.management.QuestManager;
import me.ranol.rollingquest.util.CompletionUtils;
import me.ranol.rollingquest.util.Util;

public class CmdRollingQuestAdmin implements TabExecutor {

	@Override
	public List<String> onTabComplete(CommandSender s, Command c, String l, String[] a) {
		List<String> completions = new ArrayList<>();
		if (a.length == 1) {
			completions.addAll(Arrays.asList("help", "npc", "dialog", "quest"));
			CompletionUtils.complete(completions, a[0]);
		}
		return completions;
	}

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
		if (a.length == 0 || a[0].equalsIgnoreCase("help")) {
			help(s, l);
			return true;
		} else if (a[0].equalsIgnoreCase("npc")) {
			npc(s, l, a);
			return true;
		} else if (a[0].equalsIgnoreCase("dialog")) {
			dialog(s, l, a);
			return true;
		} else if (a[0].equalsIgnoreCase("quest")) {
			quest(s, l, a);
			return true;
		}
		return true;
	}

	private void npc(CommandSender s, String l, String[] a) {
		int i = 1;
		if (a.length > 1 && a[1].matches("[0-9]+"))
			i = Integer.parseInt(a[1]);
		Util.sendMessageList(s, NpcManager.getNpcs().stream().map(npc -> npc.getName()).collect(Collectors.toList()), i,
				"/" + l + " npc");
	}

	private void dialog(CommandSender s, String l, String[] a) {
		int i = 1;
		if (a.length > 1 && a[1].matches("[0-9]+"))
			i = Integer.parseInt(a[1]);
		Util.sendMessageList(s,
				DialogManager
						.getDialogs().stream().map(dialog -> dialog.getGiver().getVisibleName() + "("
								+ dialog.getGiver().getName() + ")" + " - " + dialog.getName())
						.collect(Collectors.toList()),
				i, "/" + l + " npc");
	}

	private void quest(CommandSender s, String l, String[] a) {
		int i = 1;
		if (a.length > 1 && a[1].matches("[0-9]+"))
			i = Integer.parseInt(a[1]);
		Util.sendMessageList(s, QuestManager.getQuests().stream()
				.map(quest -> quest.getName() + " (" + quest.getDisplayName() + "§6)").collect(Collectors.toList()), i,
				"/" + l + " quest");
	}

	private void help(CommandSender s, String l) {
		s.sendMessage("§6* §e/" + l + " help §f- §a도움말을 출력합니다.");
		s.sendMessage("§6* §e/" + l + " npc [페이지] §f- §aNpc 리스트를 출력합니다.");
		s.sendMessage("§6* §e/" + l + " dialog [페이지] §f- §a다이얼로그 리스트를 출력합니다.");
		s.sendMessage("§6* §e/" + l + " quest [페이지] §f- §a퀘스트 리스트를 출력합니다.");
		s.sendMessage("§6* §e/" + l + " gquest [퀘스트명] §f- §a퀘스트를 지급합니다.");
	}

}
