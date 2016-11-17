package me.ranol.rollingquest.quest.modifiers;

import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import me.ranol.rollingquest.api.Quest;
import me.ranol.rollingquest.api.RollingModifier;

public class ModGamemode extends RollingModifier {
	String gamemode;

	@Override
	public String getString(Player p) {
		return "게임모드를 " + gamemode + "로 변경";
	}

	@Override
	public void apply(List<String> args) {
		gamemode = args.get(0).toUpperCase();
	}

	@Override
	public boolean activate(Player p) {
		return p.getGameMode() == GameMode.valueOf(gamemode);
	}
}
