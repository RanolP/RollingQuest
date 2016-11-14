package me.ranol.rollingquest.quest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;

public class DialogSet implements Cloneable {
	private String setName;
	private List<MessageDialog> dialogs = new ArrayList<>();
	private Npc giver;

	public DialogSet(String name) {
		this.setName = name;
	}

	public DialogSet setGiver(Npc npc) {
		this.giver = npc;
		return this;
	}

	public Npc getGiver() {
		return giver;
	}

	public DialogSet addDialog(MessageDialog dialog) {
		dialogs.add(dialog);
		return this;
	}

	public List<MessageDialog> getDialogs() {
		return dialogs;
	}

	public void openUI(Player p) {
		new QuestMenu(this, p).open();
	}

	@Override
	public String toString() {
		return "DialogSet [name=" + getName() + ", " + dialogs.toString() + "]";
	}

	public MessageDialog getDialog(String name) {
		MessageDialog result = null;
		List<MessageDialog> filtered = dialogs.stream().filter(d -> d.getName().equals(name))
				.collect(Collectors.toList());
		if (filtered.size() > 0)
			result = filtered.get(0);
		return result;
	}

	public String getName() {
		return setName;
	}
}
