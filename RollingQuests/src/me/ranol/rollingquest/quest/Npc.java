package me.ranol.rollingquest.quest;

import java.util.ArrayList;
import java.util.List;

import me.ranol.rollingquest.api.DialogSet;

public class Npc {
	private final String npcName;
	private String visible = "";
	private List<DialogSet> set = new ArrayList<>();

	public Npc(String name) {
		npcName = name;
	}

	public List<DialogSet> getDialogSet() {
		return set;
	}

	@Override
	public String toString() {
		return "Npc [name=" + npcName + "]";
	}

	public String getName() {
		return npcName;
	}

	public String getVisibleName() {
		return visible;
	}

	public Npc setVisibleName(String visible) {
		this.visible = visible;
		return this;
	}

	public void addDialogSet(DialogSet dialogSet) {
		set.removeIf(d -> d.getName().equals(dialogSet.getName()));
		set.add(dialogSet);
		dialogSet.setGiver(this);
	}
}
