package me.ranol.rollingquest;

import org.bukkit.event.Event;

@FunctionalInterface
public interface EventFilter<T extends Event> {
	public boolean filter(T event);
}
