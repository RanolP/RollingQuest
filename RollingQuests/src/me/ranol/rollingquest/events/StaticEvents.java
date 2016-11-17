package me.ranol.rollingquest.events;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import me.ranol.rollingquest.RollingQuest;
import me.ranol.rollingquest.menu.MenuClickEvent;

public class StaticEvents implements Listener {

	static final HashMap<Class<?>, List<BindedEvent>> binds = new HashMap<>();

	public static void initialize() {
		RollingQuest.registerEvents(new StaticEvents());
	}

	public static <T extends Event> BindedEvent<T> bind(Class<T> clazz, EventFilter<T> filter, EventRunnable<T> doRun) {
		if (!binds.containsKey(clazz))
			binds.put(clazz, new ArrayList<>());
		BindedEvent<T> bind = new BindedEvent<>(filter, doRun);
		binds.get(clazz).add(bind);
		return bind;
	}

	public static <T extends Event> void unbind(Class<T> clazz, BindedEvent<T> bind) {
		if (!binds.containsKey(clazz))
			binds.put(clazz, new ArrayList<>());
		binds.get(clazz).remove(bind);
	}

	public List<BindedEvent> getRunnables(Class<? extends Event> clazz) {
		return binds.containsKey(clazz) ? binds.get(clazz) : Collections.emptyList();
	}

	@EventHandler
	public void onMenuClick(MenuClickEvent e) {
		
		getRunnables(MenuClickEvent.class).stream().forEach(r -> r.matchedRun(e));
	}

	@EventHandler
	public void onMenuClick(EntityDeathEvent e) {
		if (e.getEntity().getKiller() != null)
			getRunnables(EntityDeathEvent.class).stream().forEach(r -> r.matchedRun(e));
	}
}
