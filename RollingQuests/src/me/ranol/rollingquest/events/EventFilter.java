package me.ranol.rollingquest.events;

import org.bukkit.event.Event;

@FunctionalInterface
public interface EventFilter<T extends Event> {
	public boolean filter(T event);
}
