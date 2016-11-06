package me.ranol.rollingquest;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

public class CmdQuestview implements TabExecutor {

	@Override
	public List<String> onTabComplete(CommandSender s, Command c, String l,
			String[] a) {
		return null;
	}

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
		// TODO Auto-generated method stub
		return false;
	}

}
