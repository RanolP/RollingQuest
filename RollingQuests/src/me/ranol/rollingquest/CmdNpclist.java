package me.ranol.rollingquest;

import java.util.List;
import java.util.stream.Collectors;

import me.ranol.rollingquest.util.Util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

public class CmdNpclist implements TabExecutor {

	@Override
	public List<String> onTabComplete(CommandSender s, Command c, String l,
			String[] a) {
		return null;
	}

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
		int i = 1;
		try {
			i = Integer.parseInt(a[0]);
		} catch (Exception e) {
		}
		Util.sendMessageList(s,
				NpcManager.getNpcs().stream().map(npc -> npc.getName())
						.collect(Collectors.toList()), i, "npclist");
		return true;
	}

}
