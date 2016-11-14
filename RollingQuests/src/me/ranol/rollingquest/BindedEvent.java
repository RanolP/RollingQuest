package me.ranol.rollingquest;

import org.bukkit.event.Event;

public class BindedEvent<T extends Event> {

	Runnable run;
	EventFilter<T> filter;

	public BindedEvent(EventFilter<T> filter, Runnable toRun) {
		this.filter = filter;
		this.run = toRun;
	}

	public void run() {
		run.run();
	}

	public boolean matched(T event) {
		return filter.filter(event);
	}

	public boolean matchedRun(T event) {
		boolean run;
		if (run = matched(event))
			run();
		return run;
	}
}
