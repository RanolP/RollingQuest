package me.ranol.rollingquest.completableactions;

import java.util.List;

import org.bukkit.entity.Player;

import me.ranol.rollingquest.api.RollingAction;
import me.ranol.rollingquest.events.StaticEvents;
import me.ranol.rollingquest.menu.MenuClickEvent;

public class ActClick extends RollingAction<MenuClickEvent> {

	@Override
	public ActClick bind() {
		setEvent(StaticEvents.bind(MenuClickEvent.class, this,
				event -> getRunnables().forEach(run -> run.run((Player) event.getWhoClicked()))));
		return this;
	}

	@Override
	public String getString(Player p) {
		return "§a>> §e아이템§a을 클릭하여 완료합니다. <<";
	}

	@Override
	public void apply(List<String> args) {

	}

	@Override
	public boolean filter(MenuClickEvent event) {
		return event.getCurrentItem().isSimilar(getQuest().toStack((Player) event.getWhoClicked()));
	}

	@Override
	public void unbind() {
		StaticEvents.unbind(MenuClickEvent.class, getEvent());
	}

}
