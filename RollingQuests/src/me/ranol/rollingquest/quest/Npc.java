package me.ranol.rollingquest.quest;

public class Npc {
	String npcName;

	public Npc(String name) {
		npcName = name;
	}

	@Override
	public String toString() {
		return "Npc [name=" + npcName + "]";
	}

	public String getName() {
		return npcName;
	}
}
