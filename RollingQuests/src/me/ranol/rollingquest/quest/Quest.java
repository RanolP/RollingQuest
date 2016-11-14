package me.ranol.rollingquest.quest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.ranol.rollingquest.completableactions.CompletableAction;
import me.ranol.rollingquest.quest.modifiers.RollingModifier;
import me.ranol.rollingquest.util.PlaceHolders;

public class Quest {

	private String questName;
	private String displayName;
	private int stackId;
	private List<RollingModifier> modifiers = new ArrayList<>();
	private CompletableAction<?> action;

	public Quest(String name) {
		this.questName = name;
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
		meta.setLore(modifiers.stream().map(mod -> modifierize(mod, p)).collect(Collectors.toList()));
		stack.setItemMeta(meta);
		return stack;
	}

	private String modifierize(RollingModifier mod, Player p) {
		return (mod.activate(p) ? "§a✓ " : "§c✗ ") + PlaceHolders.replaceAll(mod.getString(p));
	}

	public void addModifiers(RollingModifier mod) {
		System.out.println(mod);
		if (mod != null)
			modifiers.add(mod);
	}

	public void complete() {
		
	}

	public Quest setCompleteAction(CompletableAction<?> action) {
		if(this.action != null)
			this.action.unbind();
		this.action = action;
		
		return this;
	}
}
