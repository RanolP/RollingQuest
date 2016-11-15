package me.ranol.rollingquest.api;

import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;

import me.ranol.rollingquest.quest.modifiers.ModGamemode;
import me.ranol.rollingquest.quest.modifiers.ModHasItem;
import me.ranol.rollingquest.quest.modifiers.ModTakeItem;
import me.ranol.rollingquest.util.StringParser;

public interface RollingModifier {
	static final HashMap<String, Class<? extends RollingModifier>> modifiers = new HashMap<>();

	public static void initialize() {
		register("gamemode", ModGamemode.class);
		register("hasitem", ModHasItem.class);
		register("takeitem", ModTakeItem.class);
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

	public String getString(Player p);

	public void apply(List<String> args);

	public boolean activate(Player p);

	public default void complete(Player p) {
	}
}
