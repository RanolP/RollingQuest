package me.ranol.rollingquest.completableactions;

import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import me.ranol.rollingquest.BindedEvent;
import me.ranol.rollingquest.EventFilter;
import me.ranol.rollingquest.quest.Quest;
import me.ranol.rollingquest.util.StringParser;

public abstract class CompletableAction<T extends Event> implements EventFilter<T> {
	private static final HashMap<String, Class<? extends CompletableAction<?>>> modifiers = new HashMap<>();
	private Quest quest;
	private BindedEvent<T> event;

	public BindedEvent<T> getEvent() {
		return event;
	}

	public CompletableAction<T> setEvent(BindedEvent<T> event) {
		this.event = event;
		return this;
	}

	public Quest getQuest() {
		return quest;
	}

	public CompletableAction<T> setQuest(Quest quest) {
		this.quest = quest;
		return this;
	}

	public static void initialize() {
		register("click", ActClick.class);
	}

	public static void register(String name, Class<? extends CompletableAction<? extends Event>> command) {
		modifiers.put(name, command);
	}

	public static CompletableAction<?> createComplete(String args, Quest quest) {
		List<String> data = StringParser.parse(args);
		if (data.size() == 0)
			return null;
		CompletableAction<?> mod = getModifier(data.get(0));
		if (mod != null)
			mod.apply(data.subList(1, data.size()));
		mod.bind(() -> quest.complete());
		mod.setQuest(quest);
		return mod;
	}

	public static CompletableAction<?> getModifier(String name) {
		try {
			return modifiers.get(name).newInstance();
		} catch (Exception e) {
			return null;
		}
	}

	public abstract CompletableAction<T> bind(Runnable doRun);

	public abstract String getString(Player p);

	public abstract void apply(List<String> args);

	public abstract void unbind();
}
