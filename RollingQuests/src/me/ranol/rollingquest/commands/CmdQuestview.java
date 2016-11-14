package me.ranol.rollingquest.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

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
		if (a.length >= 1) {
			if (!NpcManager.availableNpc(a[0])) {
				Util.msg(s, "Npc " + a[0] + "이 존재하지 않습니다. 관리자에게 문의하세요.");
				return true;
			}
			Npc npc = NpcManager.getNpc(a[0]);
			npc.getDialogSet().openUI((Player) s);
		}
		return true;
	}

}
