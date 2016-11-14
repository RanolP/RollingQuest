package me.ranol.rollingquest.quest.commands;

import java.util.List;

import me.ranol.rollingquest.api.RollingCommand;
import me.ranol.rollingquest.quest.QuestMenu;

import org.bukkit.Location;

public class CmdTeleport implements RollingCommand {
	double x, y, z;

	@Override
	public void apply(List<String> args) {
		x = Double.parseDouble(args.get(0));
		y = Double.parseDouble(args.get(1));
		z = Double.parseDouble(args.get(2));
	}

	@Override
	public void activate(QuestMenu menu) {
		menu.getPlayer().teleport(
				new Location(menu.getPlayer().getWorld(), x, y, z));
	}

}
