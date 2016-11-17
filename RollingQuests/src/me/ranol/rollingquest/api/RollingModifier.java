package me.ranol.rollingquest.api;

import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;

import me.ranol.rollingquest.quest.modifiers.ModGamemode;
import me.ranol.rollingquest.quest.modifiers.ModHasItem;
import me.ranol.rollingquest.quest.modifiers.ModKillCount;
import me.ranol.rollingquest.quest.modifiers.ModTakeItem;
import me.ranol.rollingquest.util.StringParser;

public abstract class RollingModifier {
	static final HashMap<String, Class<? extends RollingModifier>> modifiers = new HashMap<>();
	private Quest sQuest;

	public static void initialize() {
		register("gamemode", ModGamemode.class);
		register("hasitem", ModHasItem.class);
		register("takeitem", ModTakeItem.class);
		register("kill", ModKillCount.class);
	}

	public static void register(String name, Class<? extends RollingModifier> command) {
		modifiers.put(name, command);
	}

	public static RollingModifier createModifier(String args) {
		List<String> data = StringParser.parse(args);
		if (data.size() == 0)
			return null;
		RollingModifier mod = getModifier(data.get(0));
		if (mod != null)
			mod.apply(data.subList(1, data.size()));
		return mod;
	}

	public static RollingModifier getModifier(String name) {
		try {
			return modifiers.get(name).newInstance();
		} catch (Exception e) {
			return null;
		}
	}

	public abstract String getString(Player p);

	public abstract void apply(List<String> args);

	public abstract boolean activate(Player p);

	public void complete(Player p) {
	}

	public RollingModifier setQuest(Quest quest) {
		this.sQuest = quest;
		return this;
	}

	public Quest getQuest() {
		return this.sQuest;
	}
}
