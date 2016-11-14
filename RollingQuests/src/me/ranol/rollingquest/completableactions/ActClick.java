package me.ranol.rollingquest.completableactions;

import java.util.List;

import org.bukkit.entity.Player;

import me.ranol.rollingquest.StaticEvents;
import me.ranol.rollingquest.menu.MenuClickEvent;

public class ActClick extends CompletableAction<MenuClickEvent> {

	@Override
	public ActClick bind(Runnable doRun) {
		setEvent(StaticEvents.bind(MenuClickEvent.class, this, doRun));
		return this;
	}

	@Override
	public String getString(Player p) {
		return "§a>> §e아이템§a을 클릭하세요 <<";
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
