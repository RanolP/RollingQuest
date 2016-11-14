package me.ranol.rollingquest.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import me.ranol.rollingquest.completableactions.ActClick;
import me.ranol.rollingquest.events.BindedEvent;
import me.ranol.rollingquest.events.EventFilter;
import me.ranol.rollingquest.util.PlayerRunnable;
import me.ranol.rollingquest.util.StringParser;

public abstract class RollingAction<T extends Event> implements EventFilter<T> {
	private static final HashMap<String, Class<? extends RollingAction<?>>> modifiers = new HashMap<>();
	private Quest quest;
	private BindedEvent<T> event;
	private List<PlayerRunnable> runnables = new ArrayList<>();

	public BindedEvent<T> getEvent() {
		return event;
	}

	public RollingAction<T> setEvent(BindedEvent<T> event) {
		this.event = event;
		return this;
	}

	public Quest getQuest() {
		return quest;
	}

	public RollingAction<T> setQuest(Quest quest) {
		this.quest = quest;
		return this;
	}

	public static void initialize() {
		register("click", ActClick.class);
	}

	public static void register(String name, Class<? extends RollingAction<? extends Event>> command) {
		modifiers.put(name, command);
	}

	public static RollingAction<?> createComplete(String args, Quest quest) {
		List<String> data = StringParser.parse(args);
		if (data.size() == 0)
			return null;
		RollingAction<?> mod = getAction(data.get(0));
		if (mod != null) {
			mod.bind().setQuest(quest).bindPlayer(quest::complete).apply(data.subList(1, data.size()));
		}
		return mod;
	}

	public static RollingAction<?> getAction(String name) {
		try {
			return modifiers.get(name).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public abstract RollingAction<T> bind();

	public abstract String getString(Player p);

	public abstract void apply(List<String> args);

	public abstract void unbind();

	public RollingAction<?> bindPlayer(PlayerRunnable run) {
		runnables.add(run);
		return this;
	}

	public List<PlayerRunnable> getRunnables() {
		return runnables;
	}
}
