package me.ranol.rollingquest.api;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import me.ranol.rollingquest.quest.Npc;
import me.ranol.rollingquest.quest.QuestMenu;

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
		for (MessageDialog d : dialogs) {
			if (d.getName().equals(name))
				return d;
		}
		return null;
	}

	public String getName() {
		return setName;
	}
}
