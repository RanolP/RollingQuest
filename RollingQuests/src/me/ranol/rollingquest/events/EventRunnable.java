package me.ranol.rollingquest.events;

import org.bukkit.event.Event;

@FunctionalInterface
public interface EventRunnable<T extends Event> {
	public void run(T event);
}
