package me.ranol.rollingquest.quest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;

public class Quest implements Cloneable {
	private String questName;
	private List<QuestDialog> dialogs = new ArrayList<>();
	private Npc giver;

	public Quest(String name) {
		this.questName = name;
	}

	public Quest setGiver(Npc npc) {
		this.giver = npc;
		return this;
	}

	public Npc getGiver() {
		return giver;
	}

	public Quest addDialog(QuestDialog dialog) {
		dialogs.add(dialog);
		return this;
	}

	public List<QuestDialog> getDialogs() {
		return dialogs;
	}

	public void openUI(Player p) {
		new QuestMenu(this, p).open();
	}

	@Override
	public String toString() {
		return "Quest [name=" + questName + ", " + dialogs.toString() + "]";
	}

	public QuestDialog getDialog(String name) {
		QuestDialog result = null;
		List<QuestDialog> filtered = dialogs.stream()
				.filter(d -> d.getName().equals(name))
				.collect(Collectors.toList());
		if (filtered.size() > 0)
			result = filtered.get(0);
		return result;
	}
}
