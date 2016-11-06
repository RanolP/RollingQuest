package me.ranol.rollingquest.quest;

public class Npc {
	private String npcName;
	private String visible;
	private DialogSet set;

	public Npc(String name) {
		npcName = name;
	}

	public void setDialogSet(DialogSet set) {
		this.set = set;
		if (set != null)
			set.setGiver(this);
	}

	public DialogSet getDialogSet() {
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

	public void setVisibleName(String visible) {
		this.visible = visible;
	}
}
