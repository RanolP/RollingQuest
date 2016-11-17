package me.ranol.rollingquest.quest.modifiers;

import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;

import me.ranol.rollingquest.StaticVariables;
import me.ranol.rollingquest.api.RollingModifier;
import me.ranol.rollingquest.events.BindedEvent;
import me.ranol.rollingquest.events.StaticEvents;

public class ModKillCount extends RollingModifier {
	String name;
	int count;
	BindedEvent<EntityDeathEvent> e;

	@Override
	public String getString(Player p) {
		return name + "을(를) " + count + "번 잡기. (" + StaticVariables.getInt(build(p)) + " / " + count + ")";
	}

	@Override
	public void apply(List<String> args) {
		name = args.get(0);
		try {
			count = Integer.parseInt(args.get(1));
		} catch (Exception e) {
			count = 1;
		}
		e = StaticEvents.bind(EntityDeathEvent.class, e -> {
			LivingEntity entity = e.getEntity();
			return (entity.isCustomNameVisible() ? entity.getCustomName() : entity.getType().name()).equals(name);
		}, e -> {
			String key = build(e.getEntity().getKiller());
			StaticVariables.set(key, StaticVariables.getInt(key) + 1);
		});
	}

	private String build(Player p) {
		return getQuest().getName() + "$" + getClass().getSimpleName() + "$" + p.getName();
	}

	@Override
	public boolean activate(Player p) {
		return StaticVariables.getInt(build(p)) >= count;
	}

	@Override
	public void complete(Player p) {
		StaticVariables.remove(build(p));
		StaticEvents.unbind(EntityDeathEvent.class, e);
	}

}
