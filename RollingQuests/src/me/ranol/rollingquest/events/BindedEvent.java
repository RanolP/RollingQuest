package me.ranol.rollingquest.events;

import org.bukkit.event.Event;

public class BindedEvent<T extends Event> {

	EventRunnable<T> run;
	EventFilter<T> filter;

	public BindedEvent(EventFilter<T> filter, EventRunnable<T> toRun) {
		this.filter = filter;
		this.run = toRun;
	}

	public void run(T event) {
		run.run(event);
	}

	public boolean matched(T event) {
		return filter.filter(event);
	}

	public boolean matchedRun(T event) {
		boolean run;
		if (run = matched(event))
			run(event);
		return run;
	}
}
