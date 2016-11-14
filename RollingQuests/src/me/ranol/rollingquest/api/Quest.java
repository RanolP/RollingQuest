package me.ranol.rollingquest.api;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.ranol.rollingquest.RollingQuest;
import me.ranol.rollingquest.quest.Npc;
import me.ranol.rollingquest.quest.modifiers.RollingModifier;
import me.ranol.rollingquest.util.PlaceHolders;

public class Quest {

	private String questName;
	private String displayName;
	private int stackId;
	private List<RollingModifier> modifiers = new ArrayList<>();
	private RollingAction<?> action;
	private DialogSet set;
	private Npc giver;

	public Quest(String name) {
		this.questName = name;
	}

	public void setGiver(Npc npc) {
		this.giver = npc;
	}

	public Npc getGiver() {
		return giver;
	}

	public String getName() {
		return questName;
	}

	public int getStackId() {
		return stackId;
	}

	public Quest setStackId(int stackId) {
		this.stackId = stackId;
		return this;
	}

	public Quest setDisplayName(String display) {
		displayName = display;
		return this;
	}

	public String getDisplayName() {
		return displayName;
	}

	public List<RollingModifier> getModifiers() {
		return modifiers;
	}

	public boolean isCompleted(Player p) {
		return modifiers.stream().filter(m -> m.activate(p)).count() == modifiers.size();
	}

	public ItemStack toStack(Player p) {
		ItemStack stack = new ItemStack(stackId);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(getDisplayName());
		List<String> lores = modifiers.stream().map(mod -> modifierize(mod, p)).collect(Collectors.toList());
		if (action != null && canComplete(p))
			lores.add(action.getString(p));
		meta.setLore(lores);
		stack.setItemMeta(meta);
		return stack;
	}

	private String modifierize(RollingModifier mod, Player p) {
		return (mod.activate(p) ? "§a✓ " : "§c✗ ") + PlaceHolders.replaceAll(mod.getString(p));
	}

	public void addModifiers(RollingModifier mod) {
		if (mod != null)
			modifiers.add(mod);
	}

	public boolean canComplete(Player p) {
		return getModifiers().stream().filter(mod -> !mod.activate(p)).count() <= 0;
	}

	public void complete(Player completor) {
		if (canComplete(completor)) {
			RollingQuest.addDelayedTask(() -> set.openUI(completor), 1);
		}
	}

	public Quest setCompleteAction(RollingAction<?> action) {
		if (this.action != null)
			this.action.unbind();
		this.action = action;

		return this;
	}

	public void setDialogSet(DialogSet set) {
		this.set = set;
	}

	public DialogSet getDialogSet() {
		return set;
	}
}
