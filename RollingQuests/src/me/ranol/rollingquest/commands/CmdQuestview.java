package me.ranol.rollingquest.commands;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import me.ranol.rollingquest.api.DialogSet;
import me.ranol.rollingquest.exceptions.UnknownNpcException;
import me.ranol.rollingquest.management.NpcManager;
import me.ranol.rollingquest.quest.Npc;
import me.ranol.rollingquest.util.Util;

public class CmdQuestview implements TabExecutor {

	@Override
	public List<String> onTabComplete(CommandSender s, Command c, String l, String[] a) {
		return null;
	}

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
		if (!(s instanceof Player))
			return false;
		if (a.length >= 2) {
			try {
				Npc npc = NpcManager.getNpc(a[0]);
				List<DialogSet> set = npc.getDialogSet().stream().filter(d -> d.getName().equals(a[1]))
						.collect(Collectors.toList());
				if (!set.isEmpty())
					set.get(0).openUI((Player) s);
				else
					Util.msg(s, "다이얼로그 " + a[1] + "이 존재하지 않습니다. 관리자에게 문의하세요.");
			} catch (UnknownNpcException e) {
				Util.msg(s, "Npc " + a[0] + "이 존재하지 않습니다. 관리자에게 문의하세요.");
				return true;
			}
		} else {
			Util.msg(s, "/questview <npc> <quest>");
		}
		return true;
	}

}
